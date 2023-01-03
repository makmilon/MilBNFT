package com.mobiria.bnft.ui.fragment.product_details.model

import com.mobiria.bnft.ui.fragment.home.model.PopularCollectionsItem

data class ProductDetailsResponse(
	val oData: ODataProductDetails? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)


data class ODataProductDetails(
	val product: Product? = null,
	val bid_history: ArrayList<BidHistoryItem>? = null,
	val size: ArrayList<SizeItem>? = null,
	val details: ArrayList<DetailsItem>? = null,
	val more_collections: ArrayList<PopularCollectionsItem>? = null, // MoreCollectionsItem
	val properties: ArrayList<PropertiesItem>? = null,
	val price_history: ArrayList<String>? = null
)
/*data class MoreCollectionsItem(
	val sale_end_date: String? = null,
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)*/


data class DetailsItem(
	val name: String? = null,
	val value: String? = null
)

data class PropertiesItem(
	val id: Int? = null,
	val product_id: Int? = null,
	val title: String? = null,
	val description: String? = null
)


data class Product(
	val nft_image: String? = null,
	val sale_end_date: String? = null,
	val is_favorite: Boolean? = null,
	val price: String? = null,
	val image_path: String? = null,
	val name: String? = null,
	val sale_type: String? = null,
	val description: String? = null,
	val share: String? = null,
	val id: Int? = null,
	val category_id: Int? = null,
	val nft_image2: String? = null,
	val seller_name: String? = null,
	val seller_image: String? = null,
	val min_bid_price: String? = null,
	val sale_end_date_timestamp: String? = null
)

data class SizeItem(
	val id: String? = null,
	val text: String? = null,
	val isTrue: Boolean = false
)


data class Errors(
	val any: Any? = null
)

data class BidHistoryItem(
	val date: String? = null,
	val image: String? = null,
	val price: String? = null,
	val name: String? = null,
	val text: String? = null,
	val time: String? = null
)

