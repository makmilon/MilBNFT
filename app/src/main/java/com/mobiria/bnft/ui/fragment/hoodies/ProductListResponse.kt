package com.mobiria.bnft.ui.fragment.hoodies

data class ProductListResponse(
	val oData: ODataProduct? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataProduct(
	val result: ArrayList<ProductItem>? = null,
	val next_page: Int? = null,
	val is_next: Boolean? = null
)

data class ProductItem(
	val updated_on: String? = null,
	val smart_response: Any? = null,
	val featured: Int? = null,
	val categor_name: String? = null,
	val description: String? = null,
	val sale_type: String? = null,
	val nft_storage_url: String? = null,
	val collection_id: Int? = null,
	val nft_image: String? = null,
	val sale_end_date: String? = null,
	val sale_end_date_timestamp: String? = null,
	val fav_count: Int? = null,
	val top_collection: Any? = null,
	val seller_name: Any? = null,
	val category_id: Int? = null,
	val bid_amount: Any? = null,
	val is_sold: Int? = null,
	val price: String? = null,
	val is_listed_for_sale: Int? = null,
	val product_id: Int? = null,
	val waiting_for_listing: Int? = null,
	val id: Int? = null,
	val sort_order: Int? = null,
	val json_token_url: String? = null,
	val quantity: Int? = null,
	val owner_user_id: Int? = null,
	val is_fav: Int? = null,
	val active: Int? = null,
	val list_sale_response: Any? = null,
	val created_by: Int? = null,
	val category_name: String? = null,
	val product_name: String? = null,
	val seller_image_path: Any? = null,
	val provenence: Any? = null,
	val seller_user_id: Int? = null,
	val deleted: Int? = null,
	val size: String? = null,
	val created_on: String? = null,
	val action_start_date: Any? = null,
	val image_path: String? = null,
	val name: String? = null,
	val updated_by: Int? = null,
	val view_count: Int? = null,
	val nft_image2: String? = null,
	val product_created_date: String? = null
)

data class Errors(
	val any: Any? = null
)

