package com.mobiria.bnft.model

data class IsFavoriteResponse(
	val oData: OData? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class OData(
	val action_status: Int? = null
)

data class Errors(
	val any: Any? = null
)

