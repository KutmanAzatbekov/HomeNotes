package com.geeks.homenotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.geeks.homenotes.App
import com.geeks.homenotes.ui.main.adapter.NotesAdapter
import com.geeks.homenotes.R
import com.geeks.homenotes.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val adapter = NotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.addNotes(App.Companion.database.dao().getAllNotes())
        binding.rvNotes.adapter = adapter

        binding.fbAdd.setOnClickListener { view ->
            findNavController().navigate(R.id.addNoteFragment)
        }

    }

}