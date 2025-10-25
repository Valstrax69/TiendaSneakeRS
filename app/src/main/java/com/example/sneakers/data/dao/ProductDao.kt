package com.example.sneakers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sneakers.data.entities.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>
}
