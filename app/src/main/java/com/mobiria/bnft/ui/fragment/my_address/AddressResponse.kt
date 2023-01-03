package com.mobiria.bnft.ui.fragment.my_address

data class AddressResponse(
	val oData: ArrayList<ODataAddressItem>? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataAddressItem(
	val building_name: String? = null,
	val street_address: String? = null,
	val updated_at: String? = null,
	val address_type: Int? = null,
	val user_id: Int? = null,
	val latitude: String? = null,
	val active: Int? = null,
	val created_at: String? = null,
	val id: Int? = null,
	val landmark: String? = null,
	val is_default: Int? = null,
	val longitude: String? = null
)

data class Errors(
	val any: Any? = null
)

