package com.luisitolentino.wishlist.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisitolentino.wishlist.domain.model.Wish
import com.luisitolentino.wishlist.domain.usecase.WishManagerUseCase
import com.luisitolentino.wishlist.domain.utils.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WishManagerViewModel(private val useCase: WishManagerUseCase) : ViewModel() {

    private val _stateList = MutableStateFlow<WishState>(WishState.HideLoading)
    val stateList: StateFlow<WishState> = _stateList

    private val _stateManagement = MutableStateFlow<WishManagerState>(WishManagerState.HideLoading)
    val stateManagement = _stateManagement.asStateFlow()


    fun getAllWishes(orderByName: Boolean = true) {
        viewModelScope.launch {
            _stateList.value = WishState.ShowLoading
            val response = useCase.getAllWishesByName(orderByName)
            _stateList.value = WishState.HideLoading
            response.flow({ wishes ->
                if (wishes.isNotEmpty()) _stateList.value = WishState.SearchAllSuccess(wishes)
                else _stateList.value = WishState.EmptyState
            }, {
                _stateList.value = WishState.Failure(it)
            })
        }
    }

    fun insert(wish: Wish) {
        viewModelScope.launch {
            _stateManagement.value = WishManagerState.ShowLoading
            val response = useCase.insert(wish)
            _stateManagement.value = WishManagerState.HideLoading
            response.flow({
                _stateManagement.value = WishManagerState.InsertSuccess
            }, {
                _stateManagement.value = WishManagerState.Failure(it)
            })
        }
    }

    fun update(wish: Wish) {
        viewModelScope.launch {
            _stateManagement.value = WishManagerState.ShowLoading
            val response = useCase.update(wish)
            _stateManagement.value = WishManagerState.HideLoading
            response.flow(
                {
                    _stateManagement.value = WishManagerState.UpdateSuccess
                }, {
                    _stateManagement.value = WishManagerState.Failure(it)
                }
            )
        }
    }
}

