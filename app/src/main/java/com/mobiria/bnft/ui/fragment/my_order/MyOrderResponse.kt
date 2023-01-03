package com.mobiria.bnft.ui.fragment.my_order

data class MyOrderResponse(
	val oData: ODataOrder? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class SalesHistoryItem(
	val order_no: String? = null,
	val order_date: String? = null,
	val sale_end_date: String? = null,
	val product_image: String? = null,
	val sales_price: String? = null,
	val order_id: Int? = null,
	val product_name: String? = null,
	val order_status: String? = null,
	val size: String? = null,
	val transaction_hash: String? = null
)

data class ODataOrder(
	val next_page: Int? = null,
	val is_next: Boolean? = null,
	val sales_history: ArrayList<SalesHistoryItem>? = null
)

data class Errors(
	val any: Any? = null
)

