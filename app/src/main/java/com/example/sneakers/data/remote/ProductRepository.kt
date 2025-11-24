package com.example.sneakers.data.remote

import com.example.sneakers.data.api.ApiService
import com.example.sneakers.data.entities.Product

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): List<Product> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
