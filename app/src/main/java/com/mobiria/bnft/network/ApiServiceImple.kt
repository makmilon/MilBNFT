package com.mobiria.bnft.network

import com.mobiria.bnft.model.CommonResponse
import com.mobiria.bnft.model.IsFavoriteResponse
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import kotlin.math.log

class ApiServiceImple @Inject constructor(val apiService: ApiService) {

    // FOR LOGIN USER
    suspend fun loginAPI(deviceToken: String?, walletAddress: String, walletBalance: String): LoginResponse {
        return apiService.loginAPI(
            deviceToken = deviceToken,
            walletAddress = walletAddress,
            walletBalance = walletBalance)
    }

    // FOR HOME API
    suspend fun homeAPI(): HomeResponse {
        return apiService.homeAPI()
    }

    // FOR FAVORITE API
    suspend fun myFavoriteAPI(accessToken: String): MyFavoriteResponse {
        return apiService.myFavoriteAPI(accessToken)
    }

    // IS FAVORITE API
    suspend fun isFavoriteAPI(accessToken: String, productId: String): IsFavoriteResponse {
        return apiService.isFavoriteAPI(accessToken, productId)
    }

    // PRODUCT LIST API
    suspend fun productListListAPI(accessToken: String, page: String, saleType: String?, keyword: String,
                                   sortBy: String?, collectionId: String?, category: String?, popular: String?): ProductListResponse {
        return apiService.productListListAPI(accessToken, page, saleType, keyword, sortBy, collectionId, category, popular)
    }

    // MY PRODUCT DETAILS API
    suspend fun productDetailsAPI(accessToken: String, id: String?): ProductDetailsResponse {
        return apiService.productDetailsAPI(accessToken, id)
    }

    // MY AUCTION LIST API
    suspend fun myAuctionListAPI(accessToken: String): MyAuctionResponse {
        return apiService.myAuctionAPI(accessToken)
    }

    // ADD ADDRESS API
    suspend fun addAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                              streetAddress: String,landmark: String?,addressType: String,isDefault: String): CommonResponse {
        return apiService.addAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault)
    }

    // EDIT ADDRESS API
    suspend fun editAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                              streetAddress: String,landmark: String?,addressType: String,isDefault: String, addressId: String?): CommonResponse {
        return apiService.editAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault, addressId)
    }

    // ADDRESS List API
    suspend fun addressListAPI(accessToken: String): AddressResponse {
        return apiService.addressListAPI(accessToken)
    }

    // DELETE ADDRESS API
    suspend fun deleteAddressAPI(accessToken: String, addressId: String?): CommonResponse {
        return apiService.deleteAddressAPI(accessToken, addressId)
    }

    // Place A Order API
    suspend fun placeOrderAPI(accessToken: String, productId: String, quantity: String, addressId: String, productSize: String): CommonResponse {
        return apiService.placeOrderAPI(accessToken, productId, quantity, addressId, productSize)
    }

    // My Order List API
    suspend fun myOrdersAPI(accessToken: String, page: String): MyOrderResponse {
        return apiService.myOrdersAPI(accessToken, page)
    }

    // Order Details List API
    suspend fun ordersDetailsAPI(accessToken: String, orderId: String): OrderDetailResponse {
        return apiService.ordersDetailsAPI(accessToken, orderId)
    }

    // Logout API
    suspend fun logoutAPI(accessToken: String): CommonResponse {
        return apiService.logoutAPI(accessToken)
    }

    // UPDATE PROFILE API
    suspend fun updateProfileAPI(accessToken: RequestBody, name: RequestBody?, email: RequestBody?, dialCode: RequestBody?,
    mobile: RequestBody?, thumbImage: MultipartBody.Part?): LoginResponse {
        return apiService.updateProfileAPI(accessToken, name, email, dialCode, mobile, thumbImage)
    }

    // RESALE API
    suspend fun resaleProductAPI(accessToken: String, productId: String?, salePrice: String, saleEndDate: String): CommonResponse {
        return apiService.resaleProductAPI(accessToken, productId, salePrice, saleEndDate)
    }

    // PLACE CALL API
    suspend fun placeBidAPI(accessToken: String, productId: String?, price: String): CommonResponse {
        return apiService.placeBidAPI(accessToken, productId, price)
    }

    // CONTACT US API
    suspend fun contactUsAPI(name: String, email: String, dialCode: String, phone: String, message: String): CommonResponse {
        return apiService.contactUsAPI(name, email, dialCode, phone, message)
    }

    // FOR CMS PRIVACY POLICY API
    suspend fun privacyPolicyApi(): CMSResponse {
        return apiService.privacyPolicyApi()
    }

    // FOR CMS ABOUT US API
    suspend fun aboutUsApi(): CMSResponse {
        return apiService.aboutUsApi()
    }

    // FOR CMS terms And Condition API
    suspend fun termsAndConditionApi(): CMSResponse {
        return apiService.termsAndConditionApi()
    }

    // FOR FAQ API
    suspend fun faqApi(): FaqResponse {
        return apiService.faqApi()
    }

    // FOR CATEGORY API
    suspend fun categoryApi(): CategoryResponse {
        return apiService.categoryApi()
    }
}