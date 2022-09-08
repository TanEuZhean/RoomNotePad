package com.example.noteclone.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.noteclone.data.Note
import com.example.noteclone.database.NoteRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {
    val _notes : MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
    val notes : LiveData<MutableList<String>> = _notes
    val _note : MutableLiveData<Note> = MutableLiveData<Note>()
    val note : LiveData<Note> = _note

    @JvmName("getNotes1")
    fun getNotes() : MutableLiveData<MutableList<String>> {
//        var data = sharedPreferences.getStringSet("note", null)
        val res : MutableList<String> = mutableListOf()
        viewModelScope.launch {
            val data = noteRepository.getAllNote()?.toMutableList()

            for (i in data!!) {
                res.add(i.toString())
            }
        }
        _notes.value = res

        Log.i("notesVal", _notes.value.toString())

        return _notes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNotes() : MutableList<String> {
//        var data = sharedPreferences.getStringSet("note", null)?.toMutableList()
        val res : MutableList<String> = mutableListOf()
        viewModelScope.launch {
            val data = noteRepository.getAllNote()?.toMutableList()

            for (i in data!!) {
                res.add(i.toString())
            }

            val new = Note(
                title = "New Note",
                content = "",
                time = LocalDate.now().toString(),
                color = "#FFFFFF"
            )
            noteRepository.insert(new)
            res.add(new.toString())
            Log.i("added", _notes.value.toString())
//            Log.i("new", sharedPreferences.getStringSet("note", null).toString())
        }
        _notes.value = res
        return res
    }

    fun deleteNotes(id: Int) {
//        var data = sharedPreferences.getStringSet("note", null)?.toMutableList()
        val res : MutableList<String> = mutableListOf()
        viewModelScope.launch {
            noteRepository.delete(id)

            val data = noteRepository.getAllNote()?.toMutableList()

            for(i in data!!) {
                if(i.id != id) {
                    res.add(i.toString())
                }
            }
        }
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putStringSet("note", notes.value?.toMutableSet()).apply()
        _notes.value = res
    }

    fun getNote(id: Int) {
//        var data = sharedPreferences.getStringSet("note", null)?.toMutableList()
        lateinit var res : Note
        viewModelScope.launch {
            val data = noteRepository.getNote(id)
            res = data!!
        }
        _note.value = res
    }

    fun editNote(title: String, content: String, id: Int) {
//        var data = sharedPreferences.getStringSet("note", null)?.toMutableList()
        val res : MutableList<String> = mutableListOf()
        viewModelScope.launch {
            noteRepository.update(title, content, id)

            val data = noteRepository.getAllNote()?.toMutableList()

            for(i in data!!) {
                res.add(i.toString())
            }
//            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//            editor.putStringSet("note", notes.value?.toMutableSet()).apply()
        }
        _notes.value = res
    }

    class NoteViewModelProvider(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                return NoteViewModel(noteRepository) as T
            }
            throw IllegalArgumentException("Invalid viewModel")
        }
    }
}