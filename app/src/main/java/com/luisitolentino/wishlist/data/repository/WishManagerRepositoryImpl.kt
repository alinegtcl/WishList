package com.luisitolentino.wishlist.data.repository

import com.luisitolentino.wishlist.data.datasource.WishesDao
import com.luisitolentino.wishlist.data.entity.WishEntity
import com.luisitolentino.wishlist.domain.model.Wish
import com.luisitolentino.wishlist.domain.usecase.WishManagerUseCase
import com.luisitolentino.wishlist.domain.utils.WishResult

class WishManagerRepositoryImpl(private val wishesDao: WishesDao) : WishManagerUseCase {
    override suspend fun insert(wish: Wish): WishResult<Unit, String> {
        return try {
            wishesDao.insert(toWishEntity(wish))
            WishResult.Success(Unit)
        } catch (exception: Exception) {
            WishResult.Error(exception.message.toString())
        }
    }

    override suspend fun getAllWishesByName(orderByName: Boolean): WishResult<List<Wish>, String> {
        val response = wishesDao.getAllWishesByName()
        //if(orderByName) wishesDao.getAllWishesByName()
        //else wishesDao.getAllWishesByScore()
        return if (response != null)
            WishResult.Success(toDomain(response))
        else WishResult.Error("Você não tem livros cadastrados")
    }

    private fun toDomain(wishes: List<WishEntity>): List<Wish> {
        return wishes.map {
            Wish(
                title = it.title,
                details = it.details,
                status = it.status,
                image = it.image,
                id = it.id
            )
        }
    }

    override suspend fun update(wish: Wish): WishResult<Unit, String> {
        return try {
            wishesDao.update(toWishEntity(wish))
            WishResult.Success(Unit)
        } catch (exception: Exception) {
            WishResult.Error(exception.message.toString())
        }
    }

    override suspend fun delete(wish: Wish): WishResult<Unit, String> {
        return try {
            wishesDao.delete(toWishEntity(wish))
            WishResult.Success(Unit)
        } catch (exception: Exception) {
            WishResult.Error(exception.message.toString())
        }
    }

    private fun toWishEntity(wish: Wish): WishEntity {
        return WishEntity(
            wish.id,
            wish.title,
            wish.details,
            wish.status,
            wish.image
        )
    }
}