package com.example.sneakers.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Catalog : Routes("catalog")
    object Cart : Routes("cart")
    object Login : Routes("login")
    object Register : Routes("register")
    object PreviewProduct : Routes("preview_product")
    object Profile : Routes("profile")
    object Auth : Routes("auth")
    object Checkout : Routes("checkout")
    object OrderConfirmation : Routes("order_confirmation")
}
