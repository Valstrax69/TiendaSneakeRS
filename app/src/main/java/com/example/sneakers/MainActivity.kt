package com.example.sneakers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sneakers.data.remote.CartRepository
import com.example.sneakers.data.remote.UserRepository
import com.example.sneakers.ui.components.BottomNav
import com.example.sneakers.ui.components.TopBar
import com.example.sneakers.ui.navigation.MainNavigation
import com.example.sneakers.ui.theme.SneakersTheme
import com.example.sneakers.viewmodel.AuthViewModel
import com.example.sneakers.viewmodel.AuthViewModelFactory
import com.example.sneakers.viewmodel.CartViewModel
import com.example.sneakers.viewmodel.CartViewModelFactory
import com.example.sneakers.viewmodel.CatalogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val app = LocalContext.current.applicationContext as SneakersApp
            val cartRepository = app.cartRepository
            val userRepository = app.userRepository

            SneakersApp(cartRepository, userRepository)
        }
    }
}

@Composable
fun SneakersApp(cartRepository: CartRepository, userRepository: UserRepository) {
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(userRepository))
    val cartViewModel: CartViewModel = viewModel(factory = CartViewModelFactory(cartRepository))
    val catalogViewModel: CatalogViewModel = viewModel() // No necesita factory
    
    val navController = rememberNavController()

    SneakersTheme {
        Surface {
            Scaffold(
                topBar = { TopBar(navController, authViewModel) },
                bottomBar = { BottomNav(navController, authViewModel) } // Corregido para pasar el AuthViewModel
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainNavigation(navController, authViewModel, catalogViewModel, cartViewModel)
                }
            }
        }
    }
}
