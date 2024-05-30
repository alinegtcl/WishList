package com.luisitolentino.wishlist.presentation.view.viewmodel

sealed class WishManagerState {
    data object InsertSuccess : WishManagerState()
    data object UpdateSuccess : WishManagerState()
    data object ShowLoading : WishManagerState()
    data object HideLoading : WishManagerState()
    data object DeleteSuccess : WishManagerState()
    data class Failure(val errorMessage: String) : WishManagerState()

}