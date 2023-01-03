package com.mobiria.bnft.ui.fragment.search.filter.category

data class CategoryResponse(
	val oData: ArrayList<ODataCategory>? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataCategory(
	val category_id: Int? = null,
	val name: String? = null,
	var selected: Boolean = false
)

data class Errors(
	val any: Any? = null
)

