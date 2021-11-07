package com.plcoding.cleanarchitecturenoteapp.features.notes.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.features.notes.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
