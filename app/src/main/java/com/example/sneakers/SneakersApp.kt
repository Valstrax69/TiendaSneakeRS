package com.example.sneakers

import android.app.Application
import com.example.sneakers.data.remote.CartRepository
import com.example.sneakers.data.remote.UserRepository
import com.example.sneakers.di.DatabaseModule

class SneakersApp : Application() {

    // Usamos 'lazy' para que la base de datos y los repos solo se creen cuando se necesiten por primera vez
    private val database by lazy { DatabaseModule.getDatabase(this) } // Corregido para usar DatabaseModule
    val cartRepository by lazy { CartRepository(database.cartDao()) }
    val userRepository by lazy { UserRepository(database.userDao()) }

}
