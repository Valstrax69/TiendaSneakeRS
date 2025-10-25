package com.example.sneakers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sneakers")
data class Sneaker(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val descripcion: String
)
