package com.geeks.homenotes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_list")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val desc: String,
    val timestamp: Long = System.currentTimeMillis(),
    val  color: String = "#FFFFFFFF"
): Serializable