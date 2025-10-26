package com.example.sneakers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sneakers.ui.screens.*
import com.example.sneakers.viewmodel.AuthViewModel
import com.example.sneakers.viewmodel.CartViewModel
import com.example.sneakers.viewmodel.CatalogViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    catalogViewModel: CatalogViewModel,
    cartViewModel: CartViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen(navController, catalogViewModel) }
        composable(Routes.Catalog.route) { CatalogScreen(navController, catalogViewModel) }
        composable(Routes.PreviewProduct.route) { PreviewProductScreen(navController, catalogViewModel, cartViewModel) }
        composable(Routes.Cart.route) { CartScreen(navController, cartViewModel) }
        composable(Routes.Checkout.route) { CheckoutScreen(navController, cartViewModel) }
        composable(Routes.OrderConfirmation.route) { OrderConfirmationScreen(navController) }

        composable(Routes.Login.route) { LoginScreen(navController, authViewModel) }
        composable(Routes.Register.route) { RegisterScreen(navController, authViewModel) }
        composable(Routes.Profile.route) { ProfileScreen(navController, authViewModel) }

        composable(Routes.Auth.route) {
            if (currentUser != null) {
                ProfileScreen(navController, authViewModel)
            } else {
                LoginScreen(navController, authViewModel)
            }
        }
    }
}
