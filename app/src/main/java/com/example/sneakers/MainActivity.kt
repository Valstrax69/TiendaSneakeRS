package com.example.sneakers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneakers.data.remote.CartRepository
import com.example.sneakers.data.remote.UserRepository
import com.example.sneakers.ui.components.BottomNav
import com.example.sneakers.ui.components.TopBar
import com.example.sneakers.ui.navigation.MainNavigation
import com.example.sneakers.ui.theme.SneakersTheme
import com.example.sneakers.viewmodel.SharedViewModel
import com.example.sneakers.viewmodel.SharedViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtenemos los repositorios desde nuestra clase Application
            val app = LocalContext.current.applicationContext as SneakersApp
            val cartRepository = app.cartRepository
            val userRepository = app.userRepository

            SneakersApp(cartRepository, userRepository)
        }
    }
}

@Composable
fun SneakersApp(cartRepository: CartRepository, userRepository: UserRepository) {
    // Usamos nuestro Factory para construir el ViewModel con ambos repositorios
    val sharedViewModel: SharedViewModel = viewModel(
        factory = SharedViewModelFactory(cartRepository, userRepository)
    )
    val navController = rememberNavController()

    SneakersTheme {
        Surface {
            Scaffold(
                topBar = { TopBar(navController, sharedViewModel) }, // Pasamos NavController y ViewModel al TopBar
                bottomBar = { BottomNav(navController) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainNavigation(navController, sharedViewModel)
                }
            }
        }
    }
}
