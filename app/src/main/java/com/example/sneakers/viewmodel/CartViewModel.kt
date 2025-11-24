package com.example.sneakers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sneakers.data.entities.CartItem
import com.example.sneakers.data.entities.Product
import com.example.sneakers.data.remote.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.allCartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addToCart(product: Product, size: String) = viewModelScope.launch {
        val existingItem = cartRepository.getCartItemByIdAndSize(product.id, size)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartRepository.insert(updatedItem)
        } else {
            val newCartItem = CartItem(
                id = product.id,
                name = product.name,
                description = product.description ?: "Sin descripción",
                price = product.price,
                image = product.image ?: "",
                selectedSize = size,
                quantity = 1
            )
            cartRepository.insert(newCartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.delete(cartItem)
    }

    fun increaseQuantity(item: CartItem) = viewModelScope.launch {
        val updatedItem = item.copy(quantity = item.quantity + 1)
        cartRepository.insert(updatedItem)
    }

    fun decreaseQuantity(item: CartItem) = viewModelScope.launch {
        if (item.quantity > 1) {
            val updatedItem = item.copy(quantity = item.quantity - 1)
            cartRepository.insert(updatedItem)
        } else {
            removeFromCart(item)
        }
    }

    fun clearCart() = viewModelScope.launch {
        cartRepository.clearCart()
    }
}

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
