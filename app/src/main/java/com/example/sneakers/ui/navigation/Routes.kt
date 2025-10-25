package com.example.sneakers.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Catalog : Routes("catalog")
    object Cart : Routes("cart")
    object Detail : Routes("detail")
    object Login : Routes("login")
    object Register : Routes("register")
}
