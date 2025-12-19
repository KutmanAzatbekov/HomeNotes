package com.geeks.homenotes.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.homenotes.data.models.NoteModel
import com.geeks.homenotes.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NotesAdapter(val onLongClick: (NoteModel,) -> Unit, val onClick:(NoteModel) -> Unit): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    val notesList = arrayListOf<NoteModel>()

    fun addNotes(notes: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
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

   inner class NotesViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(noteModel: NoteModel){
           binding.apply {
               tvTitle.text = noteModel.title
               tvDesc.text = noteModel.desc
               val dateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault())
               tvData.text = dateFormat.format(noteModel.timestamp)
               try {
                   val color = Color.parseColor(noteModel.color)
                   binding.root.setCardBackgroundColor(color)
               } catch (e: Exception){
                   binding.root.setCardBackgroundColor(Color.WHITE)
               }
           }

            itemView.setOnLongClickListener {
                onLongClick(noteModel)
                false
            }

            itemView.setOnClickListener {
                onClick(noteModel)
            }

        }
    }
}