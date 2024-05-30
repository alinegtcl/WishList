package com.luisitolentino.wishlist.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luisitolentino.wishlist.data.entity.WishEntity


@Dao
interface WishesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(wish: WishEntity)

    @Query("SELECT * FROM wishentity ORDER BY title")
    suspend fun getAllWishesByName(): List<WishEntity>?

//    @Query("SELECT * FROM wishentity ORDER BY score DESC")
//    suspend fun getAllWishesByScore(): List<WishEntity>?

    @Update
    suspend fun update(wish: WishEntity)

    @Delete
    suspend fun delete(wish: WishEntity)
}
