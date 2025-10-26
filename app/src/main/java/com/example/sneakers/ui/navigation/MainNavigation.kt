package com.example.sneakers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sneakers.ui.screens.CartScreen
import com.example.sneakers.ui.screens.CatalogScreen
import com.example.sneakers.ui.screens.CheckoutScreen
import com.example.sneakers.ui.screens.DetailScreen
import com.example.sneakers.ui.screens.HomeScreen
import com.example.sneakers.ui.screens.LoginScreen
import com.example.sneakers.ui.screens.OrderConfirmationScreen
import com.example.sneakers.ui.screens.PreviewProductScreen
import com.example.sneakers.ui.screens.ProfileScreen
import com.example.sneakers.ui.screens.RegisterScreen
import com.example.sneakers.viewmodel.SharedViewModel

@Composable
fun MainNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val currentUser by sharedViewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen(navController, sharedViewModel) }
        composable(Routes.Catalog.route) { CatalogScreen(navController, sharedViewModel) }
        composable(Routes.Detail.route) { DetailScreen(navController, sharedViewModel) }
        composable(Routes.Cart.route) { CartScreen(navController, sharedViewModel) }
        composable(Routes.PreviewProduct.route) { PreviewProductScreen(navController, sharedViewModel) }
        composable(Routes.Checkout.route) { CheckoutScreen(navController, sharedViewModel) }
        composable(Routes.OrderConfirmation.route) { OrderConfirmationScreen(navController) }

        composable(Routes.Login.route) { LoginScreen(navController, sharedViewModel) }
        composable(Routes.Register.route) { RegisterScreen(navController, sharedViewModel) }
        composable(Routes.Profile.route) { ProfileScreen(navController, sharedViewModel) }

        composable(Routes.Auth.route) {
            if (currentUser != null) {
                ProfileScreen(navController, sharedViewModel)
            } else {
                LoginScreen(navController, sharedViewModel)
            }
        }
    }
}
