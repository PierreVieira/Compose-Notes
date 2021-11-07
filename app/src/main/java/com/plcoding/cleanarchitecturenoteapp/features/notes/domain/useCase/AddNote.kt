package com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase

import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        when {
            note.title.isBlank() -> throw InvalidNoteException("The title of the note can't be empty.")
            note.content.isBlank() -> throw InvalidNoteException("The content of the note can't be empty.")
            else -> repository.insertNote(note)
        }
    }
}