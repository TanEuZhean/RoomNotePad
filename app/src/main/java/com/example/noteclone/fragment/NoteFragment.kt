package com.example.noteclone.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.noteclone.data.Note
import com.example.noteclone.database.Injection
import com.example.noteclone.databinding.FragmentNoteBinding
import com.example.noteclone.viewmodel.NoteViewModel
import java.lang.Integer.parseInt

class NoteFragment : Fragment() {
    private var _binding :FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        val args: Int = parseInt(requireArguments().get("Id").toString())

//        val sharedPreferences = activity?.getSharedPreferences("notepad", Context.MODE_PRIVATE)!!

        val viewModel : NoteViewModel by viewModels {
            NoteViewModel.NoteViewModelProvider(Injection.provideNoteRepository(requireContext()))
        }
        binding.viewModel = viewModel

        viewModel.getNote(args)

        val observer : Observer<Note> = Observer<Note> { newNote ->
            binding.noteTitle.setText(newNote.title)
            binding.noteText.setText(newNote.content)
        }
        viewModel.note.observe(viewLifecycleOwner, observer)

        binding.SaveButton.setOnClickListener {
            viewModel.editNote(binding.noteTitle.text.toString(), binding.noteText.text.toString(), args)
            Log.i("this",binding.noteText.text.toString()+binding.noteTitle.text.toString())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}