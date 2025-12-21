package com.geeks.homenotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.geeks.homenotes.data.models.NoteModel

@Dao
interface NoteDao {

  //  @Query("SELECT * FROM note_list ORDER BY id DESC")
   // fun getAllNotes(): List<NoteModel>

    @Query("SELECT * FROM note_list WHERE title LIKE :searchText || '%' ORDER BY id DESC")
    fun searchByTitle(searchText: String? = ""): List<NoteModel>

    @Upsert
    fun upsertNote(noteModel: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)

}