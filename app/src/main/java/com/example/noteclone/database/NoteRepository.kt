package com.example.noteclone.database

import com.example.noteclone.data.Note

class NoteRepository(private val noteDao: NoteDAO) {
    //add note
    fun insert(note: Note) : Unit {
        noteDao.insert(note)
    }

    //get all note
    fun getAllNote() : List<Note>? {
        return noteDao.getAllNote()
    }

    //get note
    fun getNote(id: Int) : Note? {
        return noteDao.getNote(id)
    }

    //edit note
    fun update(title: String, content: String, id:Int) : Unit {
        noteDao.update(title, content, id)
    }

    //delete note
    fun delete(id: Int) : Unit {
        noteDao.delete(id)
    }
}