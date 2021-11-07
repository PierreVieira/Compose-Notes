package com.plcoding.cleanarchitecturenoteapp.features.notes.presentation.addEditNote

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
