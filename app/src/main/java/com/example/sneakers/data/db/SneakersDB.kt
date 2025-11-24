package com.example.sneakers.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sneakers.data.dao.CartDao
import com.example.sneakers.data.dao.ProductDao
import com.example.sneakers.data.dao.UserDao
import com.example.sneakers.data.entities.CartItem
import com.example.sneakers.data.entities.Product
import com.example.sneakers.data.entities.User

@Database(
    entities = [Product::class, User::class, CartItem::class],
    version = 5
)
abstract class SneakersDB : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
}
