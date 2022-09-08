package com.example.noteclone.database

import androidx.room.*
import com.example.noteclone.data.Note

@Dao
interface NoteDAO {
    //add note
    @Insert
    fun insert(note: Note) : Unit

    //get all note
    @Query("SELECT * from note")
    fun getAllNote() : List<Note>?

    //get note
    @Query("SELECT * from note where id = :id")
    fun getNote(id: Int) : Note?

    //edit note
    @Query("UPDATE note SET title= :title, content= :content where id = :id")
    fun update(title: String, content: String, id:Int) : Unit

    //delete note
    @Query("DELETE from note where id=:id")
    fun delete(id: Int) : Unit
}