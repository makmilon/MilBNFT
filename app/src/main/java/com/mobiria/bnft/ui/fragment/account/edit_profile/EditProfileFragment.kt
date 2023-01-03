package com.mobiria.bnft.ui.fragment.account.edit_profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseFragment
import com.mobiria.bnft.databinding.FragmentEditProfileBinding
import com.mobiria.bnft.utils.*
import com.mobiria.bnft.utils.ui_navigation.AppNavigator
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var mVm: EditProfileViewModel

    private var path: Uri? = null
    private var currentPhotoPath: String? = null
    private val GALLERY_PIC_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentEditProfileBinding.bind(inflater.inflate(R.layout.fragment_edit_profile, container, false))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar Setup
        mActivity.toolbarVM.manageToolBar(
            true, false, false, true, false,
            "", false, false
        )
        try {
            val userData = mActivity.userLogin
            mVm = EditProfileViewModel(mActivity, userData!!)
            binding.viewModel = mVm
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // button update profile
        binding.btnBidNow.setOnClickListener {
            if (validated()){
                manageData()
            }
        }

        binding.ivEditProfile.setOnClickListener {
            callGallery()
        }
    }

    fun callGallery(){
        if (Permissions.isStoragePermissionGranted(mActivity!!)) {
            if (Permissions.isStorageReadPermissionGranted(mActivity!!)) {
                var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
                chooseFile.type = "image/*"
                chooseFile = Intent.createChooser(chooseFile, "Choose a file")
                startActivityForResult(chooseFile, GALLERY_PIC_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_PIC_REQUEST -> if (resultCode == -1) {
                val fileUri = data?.data
                val filePath: String? = AndroidUtility.getRealPathFromURI(mActivity, fileUri!!)

                if (!filePath.isNullOrEmpty()) {
                    val file: File = File(filePath)
                    if (file.exists()) {
                        currentPhotoPath = filePath
                        path = fileUri
                        binding?.ivProfile?.setImageURI(fileUri)
                    }
                }
            }
        }
    }

    private fun validated(): Boolean {
        return (!Validation.checkIsEmpty(
            binding?.etFullName,
            binding?.etEmail,
            binding?.etMobileNumber,
        ))
        return true
    }

    private fun manageData(){
        // Access Token
        val accessToken: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            pref.getStringData(Constants.ACCESS_TOKEN)!!
        )

        // full Name
        val name: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            binding?.etFullName.text?.trim().toString()
        )

        // Email
        val email: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            binding?.etEmail.text?.trim().toString()
        )

        // dial Code
        val dialCode: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            binding?.countryCodeHolder?.selectedCountryCode.toString()
        )

        // mobile
        val mobile: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            binding?.etMobileNumber.text?.trim().toString()
        )

        // Uploaded image validation and
        var thumbImageBody: MultipartBody.Part? = null

        if (path != null) {
            val thumbImage = uriToPath(path!!)
            var file: File = File(thumbImage)
            val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            thumbImageBody = MultipartBody.Part.createFormData(
                "user_image",
                file.getName(),
                requestFile
            )
        } else {
            thumbImageBody = MultipartBody.Part.createFormData("user_image", "")
        }

        if (mActivity.isInternetAvailable()) {
            updateProfileAPI(accessToken, name, email, dialCode, mobile, thumbImageBody)
        }
    }

    private fun uriToPath(imageUri: Uri): String? {
        val filePath: String? = AndroidUtility.getRealPathFromURI(mActivity, imageUri)
        return filePath
    }

    // UPDATE PROFILE API CALL
    private fun updateProfileAPI(accessToken: RequestBody, name: RequestBody?, email: RequestBody?, dialCode: RequestBody?,
                                 mobile: RequestBody?, thumbImage: MultipartBody.Part?){
        try {
            startAnim()
            mActivity?.viewModel.updateProfileAPI(
                accessToken,
                name,
                email,
                dialCode,
                mobile,
                thumbImage
            )
            mActivity?.viewModel.updateProfileLiveData.observe(viewLifecycleOwner, Observer {
                stopAnim()
                if (it.status.equals("1")) {
                    try {
                        lifecycleScope.launch {
                            // ALL USER DATA STORED
                            val gson = Gson()
                            val json = gson.toJson(it?.oData)
                            pref.saveStringData(Constants.APP_USER_DATA, json)
                            mActivity.callProfileData()
                            mActivity?.onBackPressed()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mActivity.myToast(it?.message!!)
                }
            })
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}