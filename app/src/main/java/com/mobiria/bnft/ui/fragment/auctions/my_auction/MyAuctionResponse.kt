package com.mobiria.bnft.ui.fragment.auctions.my_auction

data class MyAuctionResponse(
	val oData: ODataMyAucctions? = null,
	val message: String? = null,
	val errors: Errors? = null,
	val status: String? = null
)

data class ODataMyAucctions(
	val pending: ArrayList<MyAuctionItem>? = null,
	val soldout: ArrayList<MyAuctionItem>? = null,
	val live: ArrayList<MyAuctionItem>? = null
)

data class MyAuctionItem(
	val image: String? = null,
	val sale_end_date: String? = null,
	val price: String? = null,
	val name: String? = null,
	val id: Int? = null
)

data class Errors(
	val any: Any? = null
)

