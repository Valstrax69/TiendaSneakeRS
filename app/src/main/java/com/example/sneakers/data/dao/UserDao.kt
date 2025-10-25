package com.example.sneakers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sneakers.data.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?
}
