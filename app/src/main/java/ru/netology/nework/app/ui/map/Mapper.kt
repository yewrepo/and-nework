package ru.netology.nework.app.ui.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

fun MyMarker.getOptions(): MarkerOptions {
    return MarkerOptions().also {
        it.position(latLng)
        it.title(caption)
    }
}

fun Marker.toDto() = MyMarker(-1, position, title.orEmpty())

fun GoogleMap.addTagMarker(marker: MyMarker): Marker? {
    val mapMarker = addMarker(marker.getOptions())
    mapMarker?.tag = marker
    return mapMarker
}
