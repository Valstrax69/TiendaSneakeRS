package com.example.sneakers.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sneakers.ui.navigation.Routes
import com.example.sneakers.ui.theme.PrimaryColor

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = PrimaryColor) {
        NavigationBarItem(
            selected = currentRoute == Routes.Home.route,
            onClick = { navController.navigate(Routes.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryColor)
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Catalog.route,
            onClick = { navController.navigate(Routes.Catalog.route) },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = "Catálogo") },
            label = { Text("Catálogo") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryColor)
        )
        NavigationBarItem(
            selected = currentRoute == Routes.Cart.route,
            onClick = { navController.navigate(Routes.Cart.route) },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryColor)
        )
    }
}
