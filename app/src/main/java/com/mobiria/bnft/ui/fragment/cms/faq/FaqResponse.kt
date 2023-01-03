package com.mobiria.bnft.ui.fragment.cms.faq

data class FaqResponse(
	val oData: ArrayList<ODataItem>? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataItem(
	val description: String? = null,
	val title: String? = null
)

data class Errors(
	val any: Any? = null
)

