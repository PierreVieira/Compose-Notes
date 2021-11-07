package com.plcoding.cleanarchitecturenoteapp.features.notes.domain.useCase

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)