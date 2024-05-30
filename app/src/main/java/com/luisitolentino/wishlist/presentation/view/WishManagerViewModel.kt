package com.luisitolentino.wishlist.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.wishlist.domain.usecase.WishManagerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishManagerViewModel(private val useCase: WishManagerUseCase) : ViewModel() {

    private val _stateList = MutableStateFlow<WishState>(WishState.HideLoading)
    val stateList: StateFlow<WishState> = _stateList

    fun getAllWishes() {
        viewModelScope.launch {
            _stateList.value = WishState.ShowLoading
            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
            _stateList.value = WishState.HideLoading
            _stateList.value = WishState.EmptyState
        }
    }

}
