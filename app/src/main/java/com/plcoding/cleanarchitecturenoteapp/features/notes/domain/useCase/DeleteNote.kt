package com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase

import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }

}