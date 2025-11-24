package com.example.sneakers.data.model

import com.example.sneakers.data.entities.User

data class AuthResponse(
    val token: String?, // El token JWT, si tu backend lo usa
    val user: User      // Los datos del usuario logueado
)
