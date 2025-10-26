package com.example.sneakers.di

import android.content.Context
import androidx.room.Room
import com.example.sneakers.data.db.SneakersDB

object DatabaseModule {

    @Volatile
    private var INSTANCE: SneakersDB? = null

    fun getDatabase(context: Context): SneakersDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                SneakersDB::class.java,
                "sneakers_db"
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            INSTANCE = instance
            instance
        }
    }
}
