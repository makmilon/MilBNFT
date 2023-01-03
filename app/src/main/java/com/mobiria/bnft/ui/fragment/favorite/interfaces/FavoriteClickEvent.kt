package com.mobiria.bnft.ui.fragment.favorite.interfaces

import com.mobiria.bnft.ui.fragment.favorite.model.FavoriteItem

interface FavoriteClickEvent {
    fun onFavoriteItem(item: FavoriteItem?)
}