package com.luisitolentino.wishlist.presentation.view

import com.luisitolentino.wishlist.domain.entities.Wish

sealed class WishState {
    data object EmptyState : WishState()
    data object ShowLoading : WishState()
    data object HideLoading : WishState()
    data class SearchAllSuccess(val wishes: List<Wish>) : WishState()
    data class Failure(val message: String) : WishState()
}
