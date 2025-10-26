package com.example.sneakers.data.remote

import com.example.sneakers.data.dao.UserDao
import com.example.sneakers.data.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
