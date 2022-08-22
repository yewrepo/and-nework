package ru.netology.nework.domain

import ru.netology.nework.app.ui.map.MyMarker

interface MarkerRepository {

    fun addMarker(marker: MyMarker)

    fun removeMarker(marker: MyMarker)

    fun getMarkers(): List<MyMarker>

    fun updateMarker(marker: MyMarker)
}