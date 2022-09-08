package com.example.noteclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteclone.data.Note

@Database(entities =[Note::class], version = 1, exportSchema = false)
abstract class NoteDB:RoomDatabase() {
    abstract fun noteDao() : NoteDAO

    companion object {
        @Volatile

        private var INSTANCE: NoteDB? = null

        fun getInstance(context: Context): NoteDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?:buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NoteDB::class.java, "notePad")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}

object Injection {
    private fun provideDataSource(context: Context): NoteDB = NoteDB.getInstance(context)

    fun provideNoteRepository(context: Context): NoteRepository = NoteRepository(provideDataSource(context).noteDao())
}