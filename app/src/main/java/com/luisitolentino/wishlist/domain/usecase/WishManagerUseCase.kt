package com.luisitolentino.wishlist.domain.usecase

import com.luisitolentino.wishlist.domain.model.Wish
import com.luisitolentino.wishlist.domain.utils.WishResult

interface WishManagerUseCase {
    suspend fun insert(wish: Wish): WishResult<Unit, String>

    suspend fun getAllWishesByName(orderByName: Boolean): WishResult<List<Wish>, String>

    suspend fun update(wish: Wish): WishResult<Unit, String>

    suspend fun delete(wish: Wish): WishResult<Unit, String>
}