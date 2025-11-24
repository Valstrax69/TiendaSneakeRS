package com.example.sneakers.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val price: Double,
    val image: String?, // Ahora es opcional (puede ser nulo)
    val sizes: String?  // Ahora es opcional (puede ser nulo)
)
