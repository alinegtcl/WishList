package com.luisitolentino.wishlist.framework.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisitolentino.wishlist.data.datasource.WishesDao
import com.luisitolentino.wishlist.data.entity.WishEntity

@Database(entities = [WishEntity::class], version = 2)
abstract class WishesManagerDatabase : RoomDatabase() {

    abstract fun wishesDao(): WishesDao

    companion object {
        const val WISHES_MANAGER_DATABASE_NAME = "wishesmanager.db"
    }
}