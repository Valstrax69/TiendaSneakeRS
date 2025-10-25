package com.example.sneakers.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sneakers.data.model.Sneaker
import com.example.sneakers.data.remote.CartRepository
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val repository = CartRepository()
    val cartItems: StateFlow<List<Sneaker>> = repository.cartItems

    fun addItem(sneaker: Sneaker) = repository.addToCart(sneaker)
    fun removeItem(sneaker: Sneaker) = repository.removeFromCart(sneaker)
    fun clearCart() = repository.clearCart()
    fun totalPrice(): Double = repository.total()
}
