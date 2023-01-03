package com.mobiria.bnft.ui.fragment.my_order.order_detail

import com.mobiria.bnft.ui.fragment.product_details.model.DetailsItem

data class OrderDetailResponse(
	val oData: ODataOrderDetail? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataOrderDetail(
	val product_id: Int? = null,
	val product_name: String? = null,
	val delivery_address: DeliveryAddress? = null,
	val product_image: String? = null,
	val token_std: String? = null,
	val contract_address: String? = null,
	val nft_image: String? = null,
	val size: String? = null,
	val creator_fee: String? = null,
	val token_id: Int? = null,
	val blockchain: String? = null,
	val price: String? = null,
	val subtotal: String? = null,
	val grand_total: String? = null,
	val transaction_hash: String? = null,
	val is_resell_requested: Int? = null,
	val order_status: String? = null,
	val nft_image2: String? = null,
	val details: ArrayList<DetailsItem>? = null
)

data class DeliveryAddress(
	val id: Int? = null,
	val user_id: Int? = null,
	val latitude: String? = null,
	val longitude: String? = null,
	val building_name: String? = null,
	val street_address: String? = null,
	val landmark: String? = null,
	val active: Int? = null,
	val address_type: Int? = null,
	val is_default: Int? = null,
)

data class Errors(
	val any: Any? = null
)

