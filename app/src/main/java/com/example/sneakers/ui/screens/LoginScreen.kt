package com.example.sneakers.ui.screens


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakers.ui.navigation.Routes
import com.example.sneakers.viewmodel.AuthViewModel
import com.example.sneakers.ui.theme.SpecialColor

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle, // Puedes cambiar este icono
                contentDescription = "Logo",
                modifier = Modifier.size(64.dp),
                tint = SpecialColor
            )
            Spacer(Modifier.height(16.dp))
            Text("Bienvenido a SneakeRS :D", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
            
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = false },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError,
                singleLine = true,
                shape = CircleShape
            )
            if (emailError) {
                Text("El correo no puede estar vacío", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = false },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError,
                singleLine = true,
                shape = CircleShape
            )
            if (passwordError) {
                Text("La contraseña no puede estar vacía", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    emailError = email.isBlank()
                    passwordError = password.isBlank()
                    if (!emailError && !passwordError) {
                        authViewModel.login(email, password) { success ->
                            if (success) {
                                navController.navigate(Routes.Home.route) {
                                    popUpTo(Routes.Login.route) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar", color = SpecialColor)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate(Routes.Register.route) }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }

        IconButton(
            onClick = { showHelpDialog = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Icon(Icons.Outlined.HelpOutline, contentDescription = "Ayuda")
        }
    }

    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = { Text("Ayuda de Inicio de Sesión") },
            text = {
                Column {
                    Text("Asegúrate de que tu correo y contraseña son los mismos que usaste al registrarte.")
                    Spacer(modifier = Modifier.height(16.dp))
                    val annotatedString = buildAnnotatedString {
                        append("Si sigues teniendo problemas, visita nuestra página de ")
                        pushStringAnnotation(tag = "URL", annotation = "https://www.ejemplo.com/ayuda")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                            append("soporte")
                        }
                        pop()
                    }
                    ClickableText(text = annotatedString, onClick = { /* No hace nada momentaneamente, falta link */ })
                }
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}
