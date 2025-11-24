package com.example.sneakers.data.remote

import com.example.sneakers.data.api.ApiService
import com.example.sneakers.data.api.RetrofitInstance
import com.example.sneakers.data.dao.UserDao
import com.example.sneakers.data.entities.User
import com.example.sneakers.data.model.LoginRequest
import com.example.sneakers.data.model.RegisterRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepository(
    private val userDao: UserDao,
    private val apiService: ApiService
) {

    suspend fun loginUser(email: String, password: String): User? {
        return try {
            // Intentamos con la API
            val response = apiService.login(LoginRequest(email, password))
            
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.user
                userDao.insertUser(user) // Actualizamos caché local
                user
            } else {
                // SI LA API FALLA (o no tiene Auth), USAMOS LOCAL
                userDao.login(email, password)
            }
        } catch (e: Exception) {
            // Si no hay internet, USAMOS LOCAL
            userDao.login(email, password)
        }
    }

    suspend fun registerUser(user: User): Boolean {
        return try {
            // Intentamos registro en la API
            val request = RegisterRequest(user.name, user.email, user.password)
            val response = apiService.register(request)
            
            if (response.isSuccessful) {
                userDao.insertUser(user)
                true
            } else {
                // SI LA API FALLA (o no tiene Auth), REGISTRAMOS LOCAL
                userDao.insertUser(user)
                true
            }
        } catch (e: Exception) {
            // Si no hay internet, REGISTRAMOS LOCAL
            userDao.insertUser(user)
            true
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun uploadProfilePicture(file: File): String? {
        return try {
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)

            val response = RetrofitInstance.apiFiles.uploadFile(multipart)

            if (response.isSuccessful) {
                response.body()?.data?.url
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
