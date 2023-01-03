package com.mobiria.bnft.ui.fragment.home.model

data class HomeResponse(
	val oData: ODataHome? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class PopularCollectionsItem(
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class JustDroppedItem(
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class HoodiesItem(
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class ODataHome(
	val just_dropped: ArrayList<JustDroppedItem?>? = null,
	val featured: ArrayList<FeaturedItem?>? = null,
	val hoodies: ArrayList<HoodiesItem?>? = null,
	val popular_collections: ArrayList<PopularCollectionsItem?>? = null,
	val categories: ArrayList<CategoriesItem>? = null,
	val banners: ArrayList<BannersItem>? = null,
	val auctions: ArrayList<AuctionsItem?>? = null
)

data class FeaturedItem(
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class CategoriesItem(
	val image: String? = null,
	val category_id: Int? = null,
	val product_count: Int? = null,
	val name: String? = null
)

data class AuctionsItem(
	val sale_end_date: String? = null,
	val sale_end_date_timestamp: String? = null,
	val image_path: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class BannersItem(
	val banner_title: String? = null,
	val id: Int? = null,
	val banner_image: String? = null
)

data class Errors(
	val any: Any? = null
)

