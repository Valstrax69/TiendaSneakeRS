package com.example.sneakers.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakers.ui.navigation.Routes
import com.example.sneakers.ui.theme.SpecialColor
import com.example.sneakers.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crea tu Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Únete y obtén muchos beneficios",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; nameError = false },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError,
            singleLine = true
        )
        if (nameError) {
            Text("El nombre no puede estar vacío", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; emailError = null },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            singleLine = true
        )
        emailError?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = false },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError,
            singleLine = true
        )
        if (passwordError) {
            Text("La contraseña no puede estar vacía", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it; confirmPasswordError = null },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordError != null,
            singleLine = true
        )
        confirmPasswordError?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                nameError = name.isBlank()
                emailError = if (email.isBlank()) "El correo no puede estar vacío" else null
                passwordError = password.isBlank()
                confirmPasswordError = when {
                    confirmPassword.isBlank() -> "Debes confirmar la contraseña"
                    password != confirmPassword -> "Las contraseñas no coinciden"
                    else -> null
                }

                if (!nameError && emailError == null && !passwordError && confirmPasswordError == null) {
                    authViewModel.registerUser(name, email, password) { success ->
                        if (success) {
                            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.Login.route) {
                                popUpTo(Routes.Register.route) { inclusive = true }
                            }
                        } else {
                            emailError = "Este correo ya está en uso"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = SpecialColor)
        ) {
            Text("Registrarse")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(Routes.Login.route) }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
