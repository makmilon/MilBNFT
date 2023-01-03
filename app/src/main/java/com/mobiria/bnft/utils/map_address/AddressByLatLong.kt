package com.mobiria.bnft.utils.map_address

import android.location.Geocoder
import android.util.Log
import com.mobiria.bnft.base.BaseActivity
import com.mobiria.bnft.ui.fragment.my_address.MyAddressViewModel

class AddressByLatLong {
    companion object {
        fun getAddressFromLocation(mActivity: BaseActivity, latitude: Double, longitude: Double, viewModel: MyAddressViewModel? = null): String? {
            var result: String? = ""
            try{
                val geocoder = Geocoder(mActivity)
                val addressList = geocoder.getFromLocation(latitude, longitude, 1)
                if (addressList != null && addressList.size > 0) {
                    val address = addressList[0]

                    val sb = StringBuilder()
                    if (address.getAddressLine(0) != null){
                        sb.append(address.getAddressLine(0)) //.append("\n");
                        Log.e("perfect_location", address.getAddressLine(0))


                    }
                    result = sb.toString()
                    if (viewModel != null){
                        viewModel.mAddress.set(result)
                        viewModel.mAddressTitle.set(address.locality+"")
                    }
                }
            } catch ( e: Exception){
                e.printStackTrace()
                Log.e("perfect_location", e.message+"")
            }

            return result
        }
    }
}