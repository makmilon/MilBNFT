package com.mobiria.bnft.ui.fragment.checkout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentCheckoutBinding
import com.mobiria.bnft.ui.fragment.my_address.ODataAddressItem
import com.mobiria.bnft.ui.fragment.product_details.model.ODataProductDetails
import com.mobiria.bnft.ui.fragment.thank_you.ThankYouFragment
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class CheckoutFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCheckoutBinding
    lateinit var vm: CheckoutViewModel

    private var defaultAddress: ODataAddressItem? = null
    private var productDetails: ODataProductDetails? = null
    private var sizeText: String? = null
    private var sizeId: String? = null

    private val PERMISSION_CODE = 1001
    private var latitude:String?= null
    private var longitude:String?=null
    lateinit var mMap: GoogleMap

    fun newInstance(productDetails: ODataProductDetails?, sizeText: String, sizeId: String): CheckoutFragment {
        this.productDetails = productDetails
        this.sizeText = sizeText
        this.sizeId = sizeId
        return CheckoutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentCheckoutBinding.bind(inflater.inflate(R.layout.fragment_checkout, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.toolbarVM.manageToolBar(true, false, false, true, true,
            "Check out", false, false)
        // initialize view model
        vm = CheckoutViewModel(mActivity, productDetails!!)
        binding?.viewModel = vm
        vm.mSizeText.set(sizeText)
        vm.mSizeId.set(sizeId)

        val mapFragment = SupportMapFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.mapContainer, mapFragment, mapFragment.javaClass.name.toString()).commit()
        mapFragment?.getMapAsync(this)

        // Default Address Api Call
        if (mActivity.isInternetAvailable()){
            defaultAddressAPI()
        }

        binding?.tvOrderPlaced?.setOnClickListener {
            if (vm.mSizeId.get().isNullOrEmpty()) {
                mActivity?.myToast("Choose Product Size...")
            } else if (defaultAddress == null) {
                mActivity?.myToast("Please add Address...")
            } else {
                if (mActivity.isInternetAvailable()) {
                    placeOrderAPI(defaultAddress?.id.toString(), vm.mSizeId.get()!!)
                }
            }
        }
    }

    // Default Address API CALL
    private fun defaultAddressAPI(){
        startAnim()
        mActivity?.viewModel.addressListAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!)
        mActivity?.viewModel.addressListLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    if (!it?.oData.isNullOrEmpty()) {
                        for (i in 0 until it?.oData!!.size) {
                            if (it?.oData.get(i).is_default == 1) {
                                try {
                                    vm.isAddressNotFound.set(true)
                                    defaultAddress = it.oData?.get(i)
                                    latitude = it.oData.get(i).latitude
                                    longitude = it.oData.get(i).longitude
                                    vm.mAddress.set(
                                        it.oData.get(i).building_name + ", " + it.oData.get(i).landmark + " " + it.oData.get(
                                            i
                                        ).street_address
                                    )

                                    Load_Current_Location()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                break
                            }
                        }
                    } else {
                        defaultAddress = null
                        vm.isAddressNotFound.set(false)
                    }
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
    }


    // PLACE ORDER API CALL
    private fun placeOrderAPI(addressId: String, sizeId: String){
        startAnim()
        mActivity?.viewModel.placeOrderAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productDetails?.product?.id.toString(),"1",
            addressId, sizeId)
        mActivity.viewModel.placeOrderLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    val fragment = ThankYouFragment()
                    fragment.newInstance("#UD686725653BG")
                    mActivity.replaceFragment(fragment)
                }
            } else {
                mActivity?.myToast(it?.message!!)
            }
        })
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
                    Load_Current_Location()
                }
            } else {
                //system os is less then marshmallow
                Load_Current_Location()
            }
        }
    }

    fun Load_Current_Location() {
        if (!latitude.isNullOrEmpty() && mMap != null) {
            val latLng = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
            mMap.addMarker(
                MarkerOptions().position(latLng)
                    // .title("Marker in Sydney") // below line is use to add custom marker on our map.
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition(latLng)))
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
}