package com.geeks.homenotes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.homenotes.data.local.models.NoteModel
import com.geeks.homenotes.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NotesAdapter(): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    val notesList = arrayListOf<NoteModel>()

    fun addNotes(notes: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(notes)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
        return NotesViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int
    ) {
        holder.onBind(notesList[position])
    }

    override fun getItemCount() = notesList.size

    class NotesViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(noteModel: NoteModel){
           binding.apply {
               tvTitle.text = noteModel.title
               tvDesc.text = noteModel.desc
               val dateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault())
               tvData.text = dateFormat.format(noteModel.timestamp)
           }
        }
    }
}