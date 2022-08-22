package ru.netology.nework.app.ui.map

object None : State()
object EditMarker : State()

sealed class State

data class MapMarkersUiState(
    val state: State,
    val myMarkers: List<MyMarker> = emptyList()
)
