package com.mobiria.bnft.ui.fragment.my_address.interfaces

import com.mobiria.bnft.ui.fragment.my_address.ODataAddressItem

interface AddressClickEvent {
    fun onAddressClick(type: String, item: ODataAddressItem?)
}