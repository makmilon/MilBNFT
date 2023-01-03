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
import com.mobiria.bnft.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    // LOGIN API
    @POST("auth/wallet_login")
    @FormUrlEncoded
    suspend fun loginAPI(
        @Field("user_device_type") deviceType: String = Constants.DEVICE_TYPE,
        @Field("user_device_token") deviceToken: String?,
        @Field("wallet_address") walletAddress: String,
        @Field("wallet_balance") walletBalance: String,
    ): LoginResponse

    // HOME API
    @GET("home")
    suspend fun homeAPI(): HomeResponse

    // MY Favorite API
    @POST("my_favorites")
    @FormUrlEncoded
    suspend fun myFavoriteAPI(
        @Field("access_token") accessToken: String
    ): MyFavoriteResponse


    // Is Favorite API
    @POST("add_remove_favorites")
    @FormUrlEncoded
    suspend fun isFavoriteAPI(
        @Field("access_token") accessToken: String,
        @Field("product_id") productId: String,
    ): IsFavoriteResponse

    // Product list API (Like As Black Hoodies)
    @POST("lists")
    @FormUrlEncoded
    suspend fun productListListAPI(
        @Field("access_token") accessToken: String,
        @Field("page") page: String,
        @Field("sale_type") saleType: String?,
        @Field("keyword") keyword: String?,
        @Field("sort_by") sortBy: String?,
        @Field("collection_id") collectionId: String?,
        @Field("category") category: String?,
        @Field("popular") popular: String?
    ): ProductListResponse

    // Product list API (Like As Black Hoodies)
    @POST("product_details")
    @FormUrlEncoded
    suspend fun productDetailsAPI(
        @Field("access_token") accessToken: String,
        @Field("id") id: String?
    ): ProductDetailsResponse

    // MY AUCTION API
    @POST("my_auction")
    @FormUrlEncoded
    suspend fun myAuctionAPI(
        @Field("access_token") accessToken: String
    ): MyAuctionResponse

    // ADD ADDRESS API
    @POST("add_delivery_address")
    @FormUrlEncoded
    suspend fun addAddressAPI(
        @Field("access_token") accessToken: String,
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("building_name") buildingName: String,
        @Field("street_address") streetAddress: String,
        @Field("landmark") landmark: String?,
        @Field("address_type") addressType: String,
        @Field("is_default") isDefault: String
    ): CommonResponse

    // EDIT ADDRESS API
    @POST("edit_delivery_address")
    @FormUrlEncoded
    suspend fun editAddressAPI(
        @Field("access_token") accessToken: String,
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("building_name") buildingName: String,
        @Field("street_address") streetAddress: String,
        @Field("landmark") landmark: String?,
        @Field("address_type") addressType: String,
        @Field("is_default") isDefault: String,
        @Field("address_id") addressId: String?
    ): CommonResponse

    // ADDRESS List API
    @POST("delivery_address")
    @FormUrlEncoded
    suspend fun addressListAPI(
        @Field("access_token") accessToken: String
    ): AddressResponse

    // DELETE ADDRESS API
    @POST("remove_delivery_address")
    @FormUrlEncoded
    suspend fun deleteAddressAPI(
        @Field("access_token") accessToken: String,
        @Field("address_id") addressId: String?
    ): CommonResponse

    // PLACE ORDER API
    @POST("place_a_sale")
    @FormUrlEncoded
    suspend fun placeOrderAPI(
        @Field("access_token") accessToken: String,
        @Field("product_id") productId: String,
        @Field("quantity") quantity: String,
        @Field("delivery_address") addressId: String,
        @Field("size") productSize: String,
    ): CommonResponse

    // My Order API
    @POST("order/buy")
    @FormUrlEncoded
    suspend fun myOrdersAPI(
        @Field("access_token") accessToken: String,
        @Field("page") page: String
    ): MyOrderResponse

    // Order DETAILS API
    @POST("order_history")
    @FormUrlEncoded
    suspend fun ordersDetailsAPI(
        @Field("access_token") accessToken: String,
        @Field("order_id") orderId: String
    ): OrderDetailResponse


    // LOGOUT API
    @POST("auth/logout")
    @FormUrlEncoded
    suspend fun logoutAPI(
        @Field("access_token") accessToken: String
    ): CommonResponse


    // UPDATE PROFILE API
    @Multipart
    @POST("update_profile")
    suspend fun updateProfileAPI(
        @Part("access_token") accessToken: RequestBody?,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("dial_code") dialCode: RequestBody?,
        @Part("mobile") mobile: RequestBody?,
        @Part thumbImage: MultipartBody.Part?
    ): LoginResponse


    // RESALE API
    @POST("resell")
    @FormUrlEncoded
    suspend fun resaleProductAPI(
        @Field("access_token") accessToken: String,
        @Field("product_id") productId: String?,
        @Field("sale_price") salePrice: String,
        @Field("sale_end_date") saleEndDate: String
    ): CommonResponse

    // PLACE BID API
    @POST("place_a_bid")
    @FormUrlEncoded
    suspend fun placeBidAPI(
        @Field("access_token") accessToken: String,
        @Field("product_id") productId: String?,
        @Field("price") price: String
    ): CommonResponse


    // Contact US API
    @POST("submit_contact_us")
    @FormUrlEncoded
    suspend fun contactUsAPI(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("dial_code") dialCode: String,
        @Field("phone") phone: String,
        @Field("message") message: String
    ): CommonResponse


    // CMS privacy policy API
    @GET("privacy-policy")
    suspend fun privacyPolicyApi(): CMSResponse

    // CMS about us API
    @GET("about-us")
    suspend fun aboutUsApi(): CMSResponse

    // CMS TERMS AND CONDITIONS API
    @GET("terms")
    suspend fun termsAndConditionApi(): CMSResponse

    // CMS FAQ API
    @GET("faq")
    suspend fun faqApi(): FaqResponse

    // CMS FAQ API
    @GET("category")
    suspend fun categoryApi(): CategoryResponse
}