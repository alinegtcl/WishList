package com.luisitolentino.wishlist.presentation.view.viewmodel

import com.luisitolentino.wishlist.domain.model.Wish

sealed class WishState {
    data object EmptyState : WishState()
    data object ShowLoading : WishState()
    data object HideLoading : WishState()
    data class SearchAllSuccess(val wishes: List<Wish>) : WishState()
    data class Failure(val message: String) : WishState()
}
