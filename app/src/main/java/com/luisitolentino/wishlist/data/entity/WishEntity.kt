package com.luisitolentino.wishlist.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["title"], unique = true)])
data class WishEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val title: String,
    val details: String,
    val status: String,
    val image: String?
)