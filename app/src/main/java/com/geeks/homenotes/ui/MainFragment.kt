package com.geeks.homenotes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.geeks.homenotes.App
import com.geeks.homenotes.ui.main.adapter.NotesAdapter
import com.geeks.homenotes.R
import com.geeks.homenotes.data.models.NoteModel
import com.geeks.homenotes.databinding.FragmentMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val adapter = NotesAdapter(::onLongClick, ::onClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

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

        binding.btnLogout.setOnClickListener {
            logout()
        }

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            binding.tvAllNotes.text = it.displayName ?: "Заметки"
            Glide.with(this).load(it.photoUrl).circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground).into(binding.ivMenu)
        }



    }

    private fun onLongClick(noteModel: NoteModel){
        val  dialogView = layoutInflater.inflate(R.layout.dialog_delete, null)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).setView(dialogView).create()
        dialogView.findViewById<TextView>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                App.database.dao().deleteNote(noteModel)
                withContext(Dispatchers.Main){
                    adapter.addNotes(App.database.dao().searchByTitle())
                    dialog.dismiss()
                }
            }

        }
        dialog.show()

    }

    private fun onClick(noteModel: NoteModel){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNoteFragment(noteModel))
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)

        googleSignInClient.signOut().addOnCompleteListener {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
            findNavController().navigate(R.id.authFragment, null, navOptions)
        }
    }

}