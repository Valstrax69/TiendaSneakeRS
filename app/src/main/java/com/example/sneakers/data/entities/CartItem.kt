package com.example.sneakers.data.entities

import androidx.room.Entity

@Entity(tableName = "cart_items", primaryKeys = ["id", "selectedSize"])
data class CartItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val selectedSize: String,
    var quantity: Int = 1
)
