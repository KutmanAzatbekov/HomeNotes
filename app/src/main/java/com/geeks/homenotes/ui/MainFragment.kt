package com.geeks.homenotes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.geeks.homenotes.App
import com.geeks.homenotes.ui.main.adapter.NotesAdapter
import com.geeks.homenotes.R
import com.geeks.homenotes.data.models.NoteModel
import com.geeks.homenotes.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val adapter = NotesAdapter(::onLongClick, ::onClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.addNotes(App.database.dao().searchByTitle())
        binding.rvNotes.adapter = adapter

        binding.fbAdd.setOnClickListener { view ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNoteFragment(null))
        }

        binding.etSearch.doOnTextChanged { text, start, before, count ->
            adapter.addNotes(App.database.dao().searchByTitle(text.toString()))
        }



    }

    private fun onLongClick(noteModel: NoteModel){
        App.database.dao().deleteNote(noteModel)
        adapter.addNotes(App.database.dao().searchByTitle())
    }

    private fun onClick(noteModel: NoteModel){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNoteFragment(noteModel))
    }

}