package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.features.notes.data.dataSource.NoteDatabase
import com.plcoding.cleanarchitecturenoteapp.features.notes.data.repository.NoteRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase.AddNote
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase.DeleteNote
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase.GetNote
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase.GetNotes
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

}