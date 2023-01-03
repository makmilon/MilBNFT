package com.mobiria.bnft.ui.fragment.my_address.add_address

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentAddAddressBinding
import com.mobiria.bnft.ui.fragment.my_address.ODataAddressItem
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.Validation
import com.mobiria.bnft.utils.map_address.AddressByLatLong
import com.mobiria.bnft.utils.map_address.GpsTracker
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch


class AddAddressFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentAddAddressBinding
    private lateinit var addressVM: AddAddressViewModel

    var mBottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var isDefault: String = "0"

    // Google Maps
    private val LOCATION_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private val TO_LOCATION_REQUEST_CODE = 1
    private var latitude:String?= null
    private var longitude:String?=null
    lateinit var mMap: GoogleMap
    var apiKey: String? = "AIzaSyDpeMGQV5I2tOkgkUL4TQhdyHIbn68pSF0"
    private var item: ODataAddressItem? = null

    fun newInstance(item: ODataAddressItem?): AddAddressFragment {
        this.item = item
        return AddAddressFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAddAddressBinding.bind(inflater.inflate(R.layout.fragment_add_address, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true, "Add Address",
        false, false)

        // initialize View Model
        addressVM = AddAddressViewModel(mActivity)
        binding.viewModel = addressVM

        if (item != null){
            latitude = item?.latitude
            longitude = item?.longitude
            addressVM.mAddressText.set(item?.street_address)
            addressVM.mBuildingName.set(item?.building_name)
            addressVM.mLandmark.set(item?.landmark)
            addressVM.addressType.set(item?.address_type)
            if (item?.is_default == 1){
                binding?.sbDefaultAddress?.isChecked = true
            }
        }

      //  buildGoogleApiClient()
        // GOOGLE MAPS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED
            ) {
                //permission not granted, request it.
                val permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                //show popup for runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                //permission already granted
            }
        } else {
            //system os is less then marshmallow
        }
        val mapFragment = SupportMapFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.mapContainer, mapFragment, mapFragment.javaClass.name.toString()).commit()
        mapFragment?.getMapAsync(this)

        if (!Places.isInitialized()) {
            try {
                Places.initialize(mActivity, apiKey!!)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding?.fromLocationAddress?.setOnClickListener {
            val fields = listOf(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS
            )

            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(mActivity)
            startActivityForResult(intent, TO_LOCATION_REQUEST_CODE)
        }


        mBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        mBottomSheetBehavior?.setPeekHeight(800)
        mBottomSheetBehavior?.setHideable(false)
        mBottomSheetBehavior?.setState(BottomSheetBehavior.STATE_HALF_EXPANDED) //STATE_DRAGGING


        binding?.etStreetName?.setEnabled(false)
        binding?.btnAddAddress?.setOnClickListener {
            if (mActivity.isInternetAvailable()) {
                callApiDataManage()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED
                ) {
                    Load_Current_Location()
                } else {
                    //permission was denied
//                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == LOCATION_PICK_CODE) {
            if(latitude!= null && longitude != null) {
                loadProduct()
            }
            else{
                Load_Current_Location()}

        }

        if (requestCode == TO_LOCATION_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        latitude = place.latLng!!.latitude.toString()
                        longitude = place.latLng!!.longitude.toString()
                        if(place.latLng!!.latitude!= null &&place.latLng!!.longitude != null){
                            /*  LATITUDE = place.latLng!!.latitude.toString()
                              LONGITUDE = place.latLng!!.longitude.toString()*/
                        }
                        addressVM.mAddressText.set(place.name)
                        //ADDRESS_LAT_LNG = "$From_Latitude,$From_Longitude"

                        Add_Too_Location_Marker(place.latLng!!.latitude, place.latLng!!.longitude)

                        //Calulate_Distance()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(ContentValues.TAG, status.statusMessage!!)

                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return

        }
    }

    fun Add_Too_Location_Marker(latitude: Double, longitude: Double) {
        mMap.clear()
        val melbourneLocation = LatLng(latitude, longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(melbourneLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 15f))
    }

    private fun loadProduct() {
        try {
            Load_Location(latitude!!.toDouble(), longitude!!.toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun Load_Current_Location() {
        val gpsTracker = GpsTracker(mActivity)
        latitude = gpsTracker.getLatitude().toString()
        longitude = gpsTracker.getLongitude().toString()
        val latLng = LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())
        addressVM.mAddressText.set(AddressByLatLong.getAddressFromLocation(mActivity, latitude!!.toDouble(), longitude!!.toDouble()))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(latLng)))
        Log.e("address", ""+addressVM.mAddressText.get())
    }

    fun Load_Location(lat: Double, long: Double) {
        mMap.uiSettings.isZoomControlsEnabled = false
        val latLng = LatLng(lat, long)
        addressVM.mAddressText.set(AddressByLatLong.getAddressFromLocation(mActivity, lat, long))
        Log.e("address", ""+addressVM.mAddressText.get())
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(latLng)))
        mMap.uiSettings.isZoomControlsEnabled = true
    }


    override fun onMapReady(googleMap: GoogleMap) {
        googleMap?.apply {
            mMap = googleMap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mActivity?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission not granted, request it.
                    val permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
                    //show popup for runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    if(latitude!= null && longitude != null) {
                        loadProduct()
                    } else{
                        Load_Current_Location()
                    }
                }
            } else {
                //system os is less then marshmallow
                if(latitude!= null &&longitude != null) {
                    loadProduct()
                }
                else{
                    Load_Current_Location()
                }
            }


            mMap.setOnCameraChangeListener { cameraPosition ->
                val latLng = cameraPosition.target
                Log.e("Latitude : ", latLng.latitude.toString() + "")
                Log.e("Longitude : ", latLng.longitude.toString() + "")
                try {
                    Load_Location(latLng.latitude, latLng.longitude)
               //     addressVM.mAddressText.set(AddressByLatLong.getAddressFromLocation(mActivity,latLng.latitude, latLng.longitude))
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }

        }
    }

    private fun getCameraPosition(latLng: LatLng): CameraPosition {
        return CameraPosition.Builder()
            .target(latLng) // Sets the center of the map to location user
            .zoom(16f) // Sets the zoom
            //.bearing(90)                // Sets the orientation of the camera to east
            .tilt(40f) // Sets the tilt of the camera to 30 degrees
            .build()
    }

    private fun callApiDataManage(){
        if (validated()){
            if (binding.sbDefaultAddress.isChecked) {
                isDefault = "1"
            } else {
                isDefault = "0"
            }
            if (item == null) {
                addAddressAPI(
                    addressVM.mBuildingName.get()!!,
                    addressVM.mAddressText.get()!!,
                    addressVM.mLandmark.get(),
                )
            } else {
                editAddressAPI(
                    addressVM.mBuildingName.get()!!,
                    addressVM.mAddressText.get()!!,
                    addressVM.mLandmark.get(),
                    item?.id.toString()
                )
            }
        }
    }

    private fun validated(): Boolean {
        return (!Validation.checkIsEmpty(
            binding.etBuildingName,
            binding.etStreetName,
        ))
        return true
    }

    // Add Address API CALL
    private fun addAddressAPI(buildingName: String, streetAddress: String, landmark: String?){
        startAnim()
        mActivity?.viewModel.addAddressAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, latitude, longitude,
            buildingName, streetAddress, landmark, addressVM.addressType.get().toString(), isDefault)
        mActivity?.viewModel.addAddressLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    mActivity?.myToast(it?.message!!)
                    onBackPressed()
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }

    // EDIT Address API CALL
    private fun editAddressAPI(buildingName: String, streetAddress: String, landmark: String?, addressId: String){
        startAnim()
        mActivity?.viewModel.editAddressAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, latitude, longitude,
            buildingName, streetAddress, landmark, addressVM.addressType.get().toString(), isDefault, addressId)
        mActivity?.viewModel.editAddressLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    mActivity?.myToast(it?.message!!)
                    onBackPressed()
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }
}