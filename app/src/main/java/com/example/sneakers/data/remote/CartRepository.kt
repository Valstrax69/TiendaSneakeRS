package com.example.sneakers.data.remote

import com.example.sneakers.data.dao.CartDao
import com.example.sneakers.data.entities.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: Flow<List<CartItem>> = cartDao.getCartItems()

    suspend fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun delete(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getCartItemByIdAndSize(productId: Int, size: String): CartItem? {
        return cartDao.getCartItemByIdAndSize(productId, size)
    }
}
