package com.geeks.homenotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geeks.homenotes.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): NoteDao
}