package com.mobiria.bnft.network.app_view_model

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiria.bnft.model.CommonResponse
import com.mobiria.bnft.model.IsFavoriteResponse
import com.mobiria.bnft.network.app_repository.AppRepository
import com.mobiria.bnft.ui.auth.LoginResponse
import com.mobiria.bnft.ui.fragment.auctions.my_auction.MyAuctionResponse
import com.mobiria.bnft.ui.fragment.cms.CMSResponse
import com.mobiria.bnft.ui.fragment.cms.faq.FaqResponse
import com.mobiria.bnft.ui.fragment.favorite.model.MyFavoriteResponse
import com.mobiria.bnft.ui.fragment.home.model.HomeResponse
import com.mobiria.bnft.ui.fragment.hoodies.ProductListResponse
import com.mobiria.bnft.ui.fragment.my_address.AddressResponse
import com.mobiria.bnft.ui.fragment.my_order.MyOrderResponse
import com.mobiria.bnft.ui.fragment.my_order.order_detail.OrderDetailResponse
import com.mobiria.bnft.ui.fragment.product_details.model.ProductDetailsResponse
import com.mobiria.bnft.ui.fragment.search.filter.category.CategoryResponse
import com.mobiria.bnft.utils.DataFactory
import com.opium.app.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    // USE FOR LOGIN API
    private var _loginLiveData = SingleLiveEvent<LoginResponse>()
    val loginLiveData: SingleLiveEvent<LoginResponse>
        get() = _loginLiveData
    fun loginAPI(deviceToken: String?, walletAddress: String, walletBalance: String) {
        viewModelScope.launch {
            try {
                appRepository.loginAPI(deviceToken, walletAddress, walletBalance).collect {
                    _loginLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR HOME API
    private var _homeLiveData = SingleLiveEvent<HomeResponse>()
    val homeLiveData: SingleLiveEvent<HomeResponse>
        get() = _homeLiveData
    fun homeAPI() {
        viewModelScope.launch {
            try {
                appRepository.homeAPI().collect {
                    _homeLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR FAVORITE API
    private var _myFavoriteLiveData = SingleLiveEvent<MyFavoriteResponse>()
    val myFavoriteLiveData: SingleLiveEvent<MyFavoriteResponse>
        get() = _myFavoriteLiveData
    fun myFavoriteAPI(accessToken: String) {
        viewModelScope.launch {
            try {
                appRepository.myFavoriteAPI(accessToken).collect {
                    _myFavoriteLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR FAVORITE API
    private var _isFavoriteLiveData = SingleLiveEvent<IsFavoriteResponse>()
    val isFavoriteLiveData: SingleLiveEvent<IsFavoriteResponse>
        get() = _isFavoriteLiveData
    fun isFavoriteAPI(accessToken: String, productId: String) {
        viewModelScope.launch {
            try {
                appRepository.isFavoriteAPI(accessToken, productId).collect {
                    _isFavoriteLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR PRODUCT LIST API
    private var _productListLiveData = SingleLiveEvent<ProductListResponse>()
    val productListLiveData: SingleLiveEvent<ProductListResponse>
        get() = _productListLiveData
    fun productListListAPI(accessToken: String, page: String, saleType: String?, keyword: String,
                      sortBy: String?, collectionId: String?, category: String?, popular: String?) {
        viewModelScope.launch {
            try {
                appRepository.productListListAPI(accessToken, page, saleType, keyword, sortBy, collectionId, category, popular).collect {
                    _productListLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }



    // FOR PRODUCT DETAILS API
    private var _productDetailsLiveData = SingleLiveEvent<ProductDetailsResponse>()
    val productDetailsLiveData: SingleLiveEvent<ProductDetailsResponse>
        get() = _productDetailsLiveData
    fun productDetailsAPI(accessToken: String, id: String?) {
        viewModelScope.launch {
            try {
                appRepository.productDetailsAPI(accessToken, id).collect {
                    _productDetailsLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR MY AUCTIONS API
    private var _myAuctionsLiveData = SingleLiveEvent<MyAuctionResponse>()
    val myAuctionsLiveData: SingleLiveEvent<MyAuctionResponse>
        get() = _myAuctionsLiveData
    fun myAuctionListAPI(accessToken: String) {
        viewModelScope.launch {
            try {
                appRepository.myAuctionListAPI(accessToken).collect {
                    _myAuctionsLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR ADD ADDRESS API
    private var _addAddressLiveData = SingleLiveEvent<CommonResponse>()
    val addAddressLiveData: SingleLiveEvent<CommonResponse>
        get() = _addAddressLiveData
    fun addAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                         streetAddress: String,landmark: String?,addressType: String, isDefault: String) {
        viewModelScope.launch {
            try {
                appRepository.addAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault).collect {
                    _addAddressLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR EDIT ADDRESS API
    private var _editAddressLiveData = SingleLiveEvent<CommonResponse>()
    val editAddressLiveData: SingleLiveEvent<CommonResponse>
        get() = _editAddressLiveData
    fun editAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                         streetAddress: String,landmark: String?,addressType: String, isDefault: String, addressId: String) {
        viewModelScope.launch {
            try {
                appRepository.editAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault, addressId).collect {
                    _editAddressLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR ADDRESS LIST API
    private var _addressListLiveData = SingleLiveEvent<AddressResponse>()
    val addressListLiveData: SingleLiveEvent<AddressResponse>
        get() = _addressListLiveData
    fun addressListAPI(accessToken: String) {
        viewModelScope.launch {
            try {
                appRepository.addressListAPI(accessToken).collect {
                    _addressListLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR DELETE ADDRESS API
    private var _deleteAddressLiveData = SingleLiveEvent<CommonResponse>()
    val deleteAddressLiveData: SingleLiveEvent<CommonResponse>
        get() = _deleteAddressLiveData
    fun deleteAddressAPI(accessToken: String, addressId: String?) {
        viewModelScope.launch {
            try {
                appRepository.deleteAddressAPI(accessToken, addressId).collect {
                    _deleteAddressLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR PLACE A ORDER API
    private var _placeOrderLiveData = SingleLiveEvent<CommonResponse>()
    val placeOrderLiveData: SingleLiveEvent<CommonResponse>
        get() = _placeOrderLiveData
    fun placeOrderAPI(accessToken: String, productId: String, quantity: String, addressId: String, productSize: String) {
        viewModelScope.launch {
            try {
                appRepository.placeOrderAPI(accessToken, productId, quantity, addressId, productSize).collect {
                    _placeOrderLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // MY ORDER LIST API
    private var _myOrderLiveData = SingleLiveEvent<MyOrderResponse>()
    val myOrderLiveData: SingleLiveEvent<MyOrderResponse>
        get() = _myOrderLiveData
    fun myOrdersAPI(accessToken: String, page: String) {
        viewModelScope.launch {
            try {
                appRepository.myOrdersAPI(accessToken, page).collect {
                    _myOrderLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // ORDER Details API
    private var _orderDetailLiveData = SingleLiveEvent<OrderDetailResponse>()
    val orderDetailLiveData: SingleLiveEvent<OrderDetailResponse>
        get() = _orderDetailLiveData
    fun ordersDetailsAPI(accessToken: String, page: String) {
        viewModelScope.launch {
            try {
                appRepository.ordersDetailsAPI(accessToken, page).collect {
                    _orderDetailLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // LOGOUT API
    private var _logoutLiveData = SingleLiveEvent<CommonResponse>()
    val logoutLiveData: SingleLiveEvent<CommonResponse>
        get() = _logoutLiveData
    fun logoutAPI(accessToken: String) {
        viewModelScope.launch {
            try {
                appRepository.logoutAPI(accessToken).collect {
                    _logoutLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // UPDATE PROFILE API
    private var _updateProfileLiveData = SingleLiveEvent<LoginResponse>()
    val updateProfileLiveData: SingleLiveEvent<LoginResponse>
        get() = _updateProfileLiveData
    fun updateProfileAPI(accessToken: RequestBody, name: RequestBody?, email: RequestBody?, dialCode: RequestBody?,
                  mobile: RequestBody?, thumbImage: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                appRepository.updateProfileAPI(accessToken, name, email, dialCode, mobile, thumbImage).collect {
                    _updateProfileLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // RESALE PRODUCT API
    private var _resaleProductLiveData = SingleLiveEvent<CommonResponse>()
    val resaleProductLiveData: SingleLiveEvent<CommonResponse>
        get() = _resaleProductLiveData
    fun resaleProductAPI(accessToken: String, productId: String?, salePrice: String, saleEndDate: String) {
        viewModelScope.launch {
            try {
                appRepository.resaleProductAPI(accessToken, productId, salePrice, saleEndDate).collect {
                    _resaleProductLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // PLACE BID API
    private var _placeBidLiveData = SingleLiveEvent<CommonResponse>()
    val placeBidLiveData: SingleLiveEvent<CommonResponse>
        get() = _placeBidLiveData
    fun placeBidAPI(accessToken: String, productId: String?, price: String) {
        viewModelScope.launch {
            try {
                appRepository.placeBidAPI(accessToken, productId, price).collect {
                    _placeBidLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // CONTACT US API
    private var _contactUsLiveData = SingleLiveEvent<CommonResponse>()
    val contactUsLiveData: SingleLiveEvent<CommonResponse>
        get() = _contactUsLiveData
    fun contactUsAPI(name: String, email: String, dialCode: String, phone: String, message: String) {
        viewModelScope.launch {
            try {
                appRepository.contactUsAPI(name, email, dialCode, phone, message).collect {
                    _contactUsLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR PRIVACY POLICY API
    private var _privacyPolicyLiveData = SingleLiveEvent<CMSResponse>()
    val privacyPolicyLiveData: SingleLiveEvent<CMSResponse>
        get() = _privacyPolicyLiveData
    fun privacyPolicyApi() {
        viewModelScope.launch {
            try {
                appRepository.privacyPolicyApi().collect {
                    _privacyPolicyLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR about Us API
    private var _aboutUsLiveData = SingleLiveEvent<CMSResponse>()
    val aboutUsLiveData: SingleLiveEvent<CMSResponse>
        get() = _aboutUsLiveData
    fun aboutUsApi() {
        viewModelScope.launch {
            try {
                appRepository.aboutUsApi().collect {
                    _aboutUsLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }

    // FOR _terms And Condition API
    private var _termsAndConditionLiveData = SingleLiveEvent<CMSResponse>()
    val termsAndConditionLiveData: SingleLiveEvent<CMSResponse>
        get() = _termsAndConditionLiveData
    fun termsAndConditionApi() {
        viewModelScope.launch {
            try {
                appRepository.termsAndConditionApi().collect {
                    _termsAndConditionLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR FAQ API
    private var _faqLiveData = SingleLiveEvent<FaqResponse>()
    val faqLiveData: SingleLiveEvent<FaqResponse>
        get() = _faqLiveData
    fun faqApi() {
        viewModelScope.launch {
            try {
                appRepository.faqApi().collect {
                    _faqLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // FOR CATEGORY API
    private var _categoryLiveData = SingleLiveEvent<CategoryResponse>()
    val categoryLiveData: SingleLiveEvent<CategoryResponse>
        get() = _categoryLiveData
    fun categoryApi() {
        viewModelScope.launch {
            try {
                appRepository.categoryApi().collect {
                    _categoryLiveData.value = it
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                manageApiError(e.code().toString(), e.message.toString())
            }
        }
    }


    // Manage API Response Exception
    companion object {
        fun manageApiError(errorCode: String, errorMessage: String){
            val myIntent = Intent("API-HITTING")
            myIntent.putExtra("code", errorCode)
            myIntent.putExtra("message", errorMessage)
            myIntent.putExtra("action", "EXPIRE")
            (DataFactory.baseActivity)?.sendBroadcast(myIntent)
        }
    }
}
