package com.geeks.homenotes.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geeks.homenotes.App
import com.geeks.homenotes.R
import com.geeks.homenotes.data.models.NoteModel
import com.geeks.homenotes.databinding.FragmentAddNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    private val args: AddNoteFragmentArgs by navArgs()

    private var timeUpdateJob: Job? = null

    private var selectedColor: String = "#FFFFFFFF"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvArrow.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        args.note?.let {
            binding.etTitle.setText(it.title)
            binding.etDesc.setText(it.desc)
            binding.tvReady.text = "Обновить"
        }

        binding.ivMore.setOnClickListener {
            showMoreDialog()
        }

        binding.tvReady.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            val note: NoteModel? = args.note

                App.database.dao().upsertNote(NoteModel(title = title, desc = desc, id = note?.id, color = selectedColor))
            findNavController().navigateUp()
        }

    }

    private fun showMoreDialog(){
        val dialogView = layoutInflater.inflate(R.layout.item_dialog_note, null)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView).create()

        val  colors = listOf(dialogView.findViewById<View>(R.id.color_1) to "#FFF599",
            dialogView.findViewById<View>(R.id.color_2) to "#B69CFF",
            dialogView.findViewById<View>(R.id.color_3) to "#FD99FF",
            dialogView.findViewById<View>(R.id.color_4) to "#FF9E9E",
            dialogView.findViewById<View>(R.id.color_5) to "#91F48F",
            dialogView.findViewById<View>(R.id.color_6) to "#9EFFFF")

        colors.forEach {(view, colorCode) ->
            view.setOnClickListener {
                selectedColor = colorCode

                binding.root.setBackgroundColor(Color.parseColor(colorCode))
                dialog.dismiss()
            }
        }

        dialogView.findViewById<View>(R.id.btn_delete).setOnClickListener {
            dialog.dismiss()

            findNavController().navigateUp()
        }
        dialog.show()
    }

}