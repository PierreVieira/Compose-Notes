package com.plcoding.cleanarchitecturenoteapp.features.notes.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
