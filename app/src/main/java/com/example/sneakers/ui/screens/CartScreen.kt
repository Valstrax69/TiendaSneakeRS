package com.example.sneakers.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.sneakers.viewmodel.SharedViewModel

@Composable
fun CartScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    Text(text = "Cart Screen")
}
