package com.geeks.homenotes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geeks.homenotes.data.local.models.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_list ORDER BY id DESC")
    fun getAllNotes(): List<NoteModel>

    @Insert
    fun addNote(noteModel: NoteModel)
}