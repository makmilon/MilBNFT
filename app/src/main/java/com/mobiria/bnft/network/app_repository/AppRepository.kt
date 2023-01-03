package com.mobiria.bnft.network.app_repository

import com.google.android.gms.common.internal.service.Common
import com.mobiria.bnft.model.CommonResponse
import com.mobiria.bnft.model.IsFavoriteResponse
import com.mobiria.bnft.network.ApiServiceImple
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiServiceImple: ApiServiceImple) {

    // FOR LOGIN API
    fun loginAPI(deviceToken: String?, walletAddress: String, walletBalance: String): Flow<LoginResponse> = flow {
        emit(apiServiceImple.loginAPI(deviceToken, walletAddress, walletBalance))
    }.flowOn(Dispatchers.IO)


    // FOR HOME API
    fun homeAPI(): Flow<HomeResponse> = flow {
        emit(apiServiceImple.homeAPI())
    }.flowOn(Dispatchers.IO)


    // FOR MY FAVORITE LIST API
    fun myFavoriteAPI(accessToken: String): Flow<MyFavoriteResponse> = flow {
        emit(apiServiceImple.myFavoriteAPI(accessToken))
    }.flowOn(Dispatchers.IO)

    // FOR IS FAVORITE API
    fun isFavoriteAPI(accessToken: String, productId: String): Flow<IsFavoriteResponse> = flow {
        emit(apiServiceImple.isFavoriteAPI(accessToken, productId))
    }.flowOn(Dispatchers.IO)

    // FOR PRODUCT LIST API
    fun productListListAPI(accessToken: String, page: String, saleType: String?, keyword: String,
                           sortBy: String?, collectionId: String?, category: String?, popular: String?): Flow<ProductListResponse> = flow {
        emit(apiServiceImple.productListListAPI(accessToken, page, saleType, keyword, sortBy, collectionId, category, popular))
    }.flowOn(Dispatchers.IO)

    // FOR PRODUCT DETAILS API
    fun productDetailsAPI(accessToken: String, id: String?): Flow<ProductDetailsResponse> = flow {
        emit(apiServiceImple.productDetailsAPI(accessToken, id))
    }.flowOn(Dispatchers.IO)

    // FOR MY AUCTION API
    fun myAuctionListAPI(accessToken: String): Flow<MyAuctionResponse> = flow {
        emit(apiServiceImple.myAuctionListAPI(accessToken))
    }.flowOn(Dispatchers.IO)

    // FOR ADD ADDRESS API
    fun addAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                      streetAddress: String,landmark: String?,addressType: String,isDefault: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.addAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault))
    }.flowOn(Dispatchers.IO)

    // FOR EDIT ADDRESS API
    fun editAddressAPI(accessToken: String,latitude: String?,longitude: String?,buildingName: String,
                      streetAddress: String,landmark: String?,addressType: String,isDefault: String, addressId: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.editAddressAPI(accessToken, latitude, longitude, buildingName, streetAddress, landmark, addressType, isDefault, addressId))
    }.flowOn(Dispatchers.IO)

    // FOR  ADDRESS LIST API
    fun addressListAPI(accessToken: String): Flow<AddressResponse> = flow {
        emit(apiServiceImple.addressListAPI(accessToken))
    }.flowOn(Dispatchers.IO)

    // FOR DELETE ADDRESS API
    fun deleteAddressAPI(accessToken: String, addressId: String?): Flow<CommonResponse> = flow {
        emit(apiServiceImple.deleteAddressAPI(accessToken, addressId))
    }.flowOn(Dispatchers.IO)

    // FOR PLACE A ORDER API
    fun placeOrderAPI(accessToken: String, productId: String, quantity: String, addressId: String, productSize: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.placeOrderAPI(accessToken, productId, quantity, addressId, productSize))
    }.flowOn(Dispatchers.IO)

    // My ORDER LIST API
    fun myOrdersAPI(accessToken: String, page: String): Flow<MyOrderResponse> = flow {
        emit(apiServiceImple.myOrdersAPI(accessToken, page))
    }.flowOn(Dispatchers.IO)

    // ORDER DETAILS API
    fun ordersDetailsAPI(accessToken: String, orderId: String): Flow<OrderDetailResponse> = flow {
        emit(apiServiceImple.ordersDetailsAPI(accessToken, orderId))
    }.flowOn(Dispatchers.IO)

    // LOGOUT API
    fun logoutAPI(accessToken: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.logoutAPI(accessToken))
    }.flowOn(Dispatchers.IO)

    // UPDATE PROFILE API
    fun updateProfileAPI(accessToken: RequestBody, name: RequestBody?, email: RequestBody?, dialCode: RequestBody?,
                  mobile: RequestBody?, thumbImage: MultipartBody.Part?): Flow<LoginResponse> = flow {
        emit(apiServiceImple.updateProfileAPI(accessToken, name, email, dialCode, mobile, thumbImage))
    }.flowOn(Dispatchers.IO)

    // RESALE PRODUCT API
    fun resaleProductAPI(accessToken: String, productId: String?, salePrice: String, saleEndDate: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.resaleProductAPI(accessToken, productId, salePrice, saleEndDate))
    }.flowOn(Dispatchers.IO)

    // PLACE BID API
    fun placeBidAPI(accessToken: String, productId: String?, price: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.placeBidAPI(accessToken, productId, price))
    }.flowOn(Dispatchers.IO)

    // CONTACT US API
    fun contactUsAPI(name: String, email: String, dialCode: String, phone: String, message: String): Flow<CommonResponse> = flow {
        emit(apiServiceImple.contactUsAPI(name, email, dialCode, phone, message))
    }.flowOn(Dispatchers.IO)


    // FOR PRIVACY POLICY API
    fun privacyPolicyApi(): Flow<CMSResponse> = flow {
        emit(apiServiceImple.privacyPolicyApi())
    }.flowOn(Dispatchers.IO)


    // FOR about Us API
    fun aboutUsApi(): Flow<CMSResponse> = flow {
        emit(apiServiceImple.aboutUsApi())
    }.flowOn(Dispatchers.IO)

    // FOR terms And Condition API
    fun termsAndConditionApi(): Flow<CMSResponse> = flow {
        emit(apiServiceImple.termsAndConditionApi())
    }.flowOn(Dispatchers.IO)

    // FOR FAQ API
    fun faqApi(): Flow<FaqResponse> = flow {
        emit(apiServiceImple.faqApi())
    }.flowOn(Dispatchers.IO)

    // FOR CATEGORY API
    fun categoryApi(): Flow<CategoryResponse> = flow {
        emit(apiServiceImple.categoryApi())
    }.flowOn(Dispatchers.IO)
}