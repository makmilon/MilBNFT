package com.mobiria.bnft.ui.fragment.search.filter.category


interface CategoryClickEvent {
    fun onCategoryClick(type: String, item: ODataCategory?)
}