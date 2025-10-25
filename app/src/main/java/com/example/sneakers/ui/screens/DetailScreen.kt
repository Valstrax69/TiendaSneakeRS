package com.example.sneakers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakers.viewmodel.SharedViewModel

@Composable
fun DetailScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalle del Producto", style = MaterialTheme.typography.titleLarge)
        Text("Aquí se verá la información detallada del producto.")
    }
}
