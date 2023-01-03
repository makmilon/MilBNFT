package com.mobiria.bnft.ui.fragment.my_order.order_detail

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
import com.mobiria.bnft.databinding.FragmentOrderDetailBinding
import com.mobiria.bnft.interfaces.DialogListener
import com.mobiria.bnft.ui.fragment.my_order.resale_interface.ResaleListener
import com.mobiria.bnft.utils.Constants
import com.mobiria.bnft.utils.dialog.CustomDialog
import com.mobiria.bnft.utils.myToast
import kotlinx.coroutines.launch

class OrderDetailsFragment : BaseFragment(), ResaleListener, OnMapReadyCallback {
    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var vm: OrderDetailViewModel

    private val PERMISSION_CODE = 1001
    private var latitude:String?= null
    private var longitude:String?=null
    lateinit var mMap: GoogleMap

    private var orderId: String = ""
    private var orderNumber: String = ""

   fun newInstance(orderId: String, orderNumber: String): OrderDetailsFragment {
       this.orderId = orderId
       this.orderNumber = orderNumber
       return OrderDetailsFragment()
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentOrderDetailBinding.bind(inflater.inflate(R.layout.fragment_order_detail, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            mActivity.toolbarVM.manageToolBar(
                true, false, false, true, true,
                orderNumber, false, false
            )

            // MAP
            val mapFragment = SupportMapFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.mapContainer, mapFragment, mapFragment.javaClass.name.toString()).commit()
            mapFragment?.getMapAsync(this)

            if (mActivity.isInternetAvailable()) {
                orderDetailsAPI()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Order DETAILS API CALL
    private fun orderDetailsAPI(){
        startAnim()
        mActivity.viewModel.ordersDetailsAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, orderId)
        mActivity.viewModel.orderDetailLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    vm = OrderDetailViewModel(mActivity, it?.oData!!, this@OrderDetailsFragment)
                    binding.viewModel = vm
                    latitude = it.oData.delivery_address?.latitude
                    longitude = it.oData.delivery_address?.longitude
                    Load_Current_Location()
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



    override fun setValue(productId: String?, resalePrice: String?, endDate: String?) {
        if (mActivity.isInternetAvailable()) {
            if (resalePrice.isNullOrEmpty()) {
                mActivity.myToast("Please Enter Price...")
            } else if (endDate.isNullOrEmpty()) {
                mActivity.myToast("Please Select End Date...")
            } else {
                resaleProductAPI(productId, resalePrice, endDate)
            }
        }
    }


    // Order DETAILS API CALL
    private fun resaleProductAPI(productId: String?, salePrice: String, endDate: String){
        startAnim()
        mActivity?.viewModel.resaleProductAPI(pref.getStringData(Constants.ACCESS_TOKEN)!!, productId, salePrice, endDate)
        mActivity?.viewModel.resaleProductLiveData.observe(viewLifecycleOwner, Observer {
            stopAnim()
            if (it.status.equals("1")) {
                lifecycleScope.launch {
                    CustomDialog.showWelcomeDialog(mActivity, it?.message, object : DialogListener {
                        override fun setValue(value: String, position: Int) {
                            mActivity.onBackPressed()
                        }
                    })
                }
            } else {
                CustomDialog.showWelcomeDialog(mActivity, it?.message, object : DialogListener {
                    override fun setValue(value: String, position: Int) {
                    }
                })
            }
        })
    }

}