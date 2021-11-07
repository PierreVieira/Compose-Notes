package com.plcoding.cleanarchitecturenoteapp.features.notes.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val NAME = "notes_db"
    }
}