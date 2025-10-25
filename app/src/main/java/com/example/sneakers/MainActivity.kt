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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sneakers.ui.components.BottomNav
import com.example.sneakers.ui.components.TopBar
import com.example.sneakers.ui.navigation.MainNavigation
import com.example.sneakers.ui.theme.SneakersTheme
import com.example.sneakers.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SneakersApp()
        }
    }
}

@Composable
fun SneakersApp() {
    val sharedViewModel: SharedViewModel = viewModel()
    val navController = rememberNavController()

    SneakersTheme {
        Surface {
            Scaffold(
                topBar = { TopBar() },
                bottomBar = { BottomNav(navController) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainNavigation(navController, sharedViewModel)
                }
            }
        }
    }
}
