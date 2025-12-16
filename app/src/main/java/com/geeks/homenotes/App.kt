package com.geeks.homenotes

import android.app.Application
import androidx.room.Room
import com.geeks.homenotes.data.local.AppDatabase

class App : Application() {

    companion object{
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
            "data_base").allowMainThreadQueries().build()
    }

}