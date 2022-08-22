package ru.netology.nework.data

import ru.netology.nework.app.ui.map.MyMarker
import ru.netology.nework.domain.MarkerRepository

class MarkerRepositoryImpl : MarkerRepository {

    private val markerList = mutableListOf<MyMarker>()

    override fun addMarker(marker: MyMarker) {
        markerList.add(marker.copy(id = getMarkers().size))
    }

    override fun removeMarker(marker: MyMarker) {
        markerList.remove(marker)
    }

    override fun getMarkers() = markerList

    override fun updateMarker(marker: MyMarker) {
        markerList
            .replaceAll {
                return@replaceAll if (it.id == marker.id) {
                    it.copy(caption = marker.caption)
                } else {
                    it
                }
            }
    }
}