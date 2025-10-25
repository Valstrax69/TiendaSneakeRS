package com.example.sneakers.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.sneakers.data.entities.Product

class SharedViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product) {
        _cartItems.value = _cartItems.value + product
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value - product
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.price }
    }
}
