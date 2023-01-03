package com.mobiria.bnft.ui.fragment.favorite.model

data class MyFavoriteResponse(
	val oData: ODataFavorite? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class FavoriteItem(
	val sale_end_date: String? = null,
	val price: String? = null,
	val image_path: String? = null,
	val name: String? = null,
	val sale_type: String? = null,
	val id: Int? = null
)

data class ODataFavorite(
	val next_page: Int? = null,
	val is_next: Boolean? = null,
	val total: Int? = null,
	val data: ArrayList<FavoriteItem>? = null
)

data class Errors(
	val any: Any? = null
)

