package com.geeks.homenotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geeks.homenotes.App
import com.geeks.homenotes.R
import com.geeks.homenotes.data.local.models.NoteModel
import com.geeks.homenotes.databinding.FragmentAddNoteBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    private var timeUpdateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startRealTimeUpdate()

        binding.tvArrow.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        binding.tvReady.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()

            App.Companion.database.dao().addNote(NoteModel(title = title, desc = desc))
            findNavController().navigateUp()
        }

    }

    private fun startRealTimeUpdate(){
        timeUpdateJob?.cancel()
        timeUpdateJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true){
                val currentTime = System.currentTimeMillis()
                val dateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault())
                binding.tvTime.text = dateFormat.format(currentTime)
                delay(1000)
            }
        }
    }

}