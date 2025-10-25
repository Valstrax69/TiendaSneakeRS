package com.example.sneakers.data.remote

import com.example.sneakers.data.model.Sneaker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {
    private val _cartItems = MutableStateFlow<List<Sneaker>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(sneaker: Sneaker) {
        _cartItems.value = _cartItems.value + sneaker
    }

    fun removeFromCart(sneaker: Sneaker) {
        _cartItems.value = _cartItems.value - sneaker
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun total(): Double = _cartItems.value.sumOf { it.precio }
}
