package com.example.sneakers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakers.ui.navigation.Routes
import com.example.sneakers.viewmodel.CartViewModel
import com.example.sneakers.ui.theme.SpecialColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController, cartViewModel: CartViewModel) {
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var addressError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var zipCodeError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var expiryDateError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Dirección de Envío", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = address, onValueChange = { address = it; addressError = null }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth(), isError = addressError != null, singleLine = true)
            addressError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = city, onValueChange = { city = it; cityError = null }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth(), isError = cityError != null, singleLine = true)
            cityError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = zipCode, onValueChange = { zipCode = it; zipCodeError = null }, label = { Text("Código Postal") }, modifier = Modifier.fillMaxWidth(), isError = zipCodeError != null, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
            zipCodeError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }

            Spacer(Modifier.height(32.dp))

            Text("Método de Pago", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = cardNumber, onValueChange = { cardNumber = it; cardNumberError = null }, label = { Text("Número de Tarjeta") }, modifier = Modifier.fillMaxWidth(), isError = cardNumberError != null, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
            cardNumberError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = expiryDate, onValueChange = { expiryDate = it; expiryDateError = null }, label = { Text("Fecha de Vencimiento (MM/AA)") }, modifier = Modifier.fillMaxWidth(), isError = expiryDateError != null, singleLine = true)
            expiryDateError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = cvv, onValueChange = { cvv = it; cvvError = null }, label = { Text("CVV") }, modifier = Modifier.fillMaxWidth(), isError = cvvError != null, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
            cvvError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    var isValid = true
                    if (address.isBlank()) { addressError = "La dirección es obligatoria"; isValid = false }
                    if (city.isBlank()) { cityError = "La ciudad es obligatoria"; isValid = false }
                    if (zipCode.isBlank()) { zipCodeError = "El código postal es obligatorio"; isValid = false }
                    if (!cardNumber.matches(Regex("^\\d{16}$"))) { cardNumberError = "Debe tener 16 dígitos numéricos"; isValid = false }
                    if (!expiryDate.matches(Regex("^(0[1-9]|1[0-2])/?([0-9]{2})$"))) { expiryDateError = "Formato inválido (MM/AA)"; isValid = false }
                    if (!cvv.matches(Regex("^\\d{3,4}$"))) { cvvError = "Debe tener 3 o 4 dígitos"; isValid = false }

                    if (isValid) {
                        cartViewModel.clearCart()
                        navController.navigate(Routes.OrderConfirmation.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = SpecialColor)
            ) {
                Text("Pagar Ahora")
            }
        }
    }
}
