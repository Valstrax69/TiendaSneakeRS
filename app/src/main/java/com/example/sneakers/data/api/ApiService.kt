package com.example.sneakers.data.api

import com.example.sneakers.data.entities.Product
import com.example.sneakers.data.model.AuthResponse
import com.example.sneakers.data.model.LoginRequest
import com.example.sneakers.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Autenticación
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    // Productos
    @GET("api/products")
    suspend fun getProducts(): Response<List<Product>>
}
