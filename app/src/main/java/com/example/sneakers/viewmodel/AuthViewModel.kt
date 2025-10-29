package com.example.sneakers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sneakers.data.entities.User
import com.example.sneakers.data.remote.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) = viewModelScope.launch {
        val user = userRepository.loginUser(email, password)
        _currentUser.value = user
        onResult(user != null)
    }

    fun registerUser(name: String, email: String, password: String, onResult: (Boolean) -> Unit) = viewModelScope.launch {
        if (userRepository.getUserByEmail(email) != null) {
            onResult(false)
            return@launch
        }
        val newUser = User(name = name, email = email, password = password)
        userRepository.registerUser(newUser)
        onResult(true)
    }

    fun logout() {
        _currentUser.value = null
    }
}

class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
