package com.mobiria.bnft.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.mobiria.bnft.base.BaseActivity

class Permissions {
    companion object {

        fun isStoragePermissionGranted(activity: BaseActivity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted")
                    true
                } else {
                    Log.v("TAG", "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted")
                true
            }
        }
        fun isStorageReadPermissionGranted(activity: BaseActivity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted")
                    true
                } else {
                    Log.v("TAG", "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted")
                true
            }
        }

        fun isCameraPermissionGranted(activity: BaseActivity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.CAMERA) === PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted")
                    true
                } else {
                    Log.v("TAG", "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.CAMERA),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted")
                true
            }
        }

        fun isLocationPermissionGranted(activity: BaseActivity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted")
                    true
                } else {
                    Log.v("TAG", "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted")
                true
            }
        }
    }
}