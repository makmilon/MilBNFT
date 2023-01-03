package com.mobiria.bnft.ui.fragment.cms

data class CMSResponse(
	val oData: ODataCMS? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataCMS(
	val page_title: String? = null,
	val description: String? = null,
	val id: String? = null,
	val title: String? = null
)

data class Errors(
	val any: Any? = null
)

