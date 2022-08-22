package ru.netology.nework.app.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import ru.netology.nework.app.ui.map.MyMarker
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

data class CoordinatesData(
    val lat: Double,
    val long: Double
) : Parcelable {

    fun toMarker(): MyMarker {
        TODO("Not yet implemented")
    }

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoordinatesData> {
        override fun createFromParcel(parcel: Parcel): CoordinatesData {
            return CoordinatesData(parcel)
        }

        override fun newArray(size: Int): Array<CoordinatesData?> {
            return arrayOfNulls(size)
        }
    }
}

object CoordinatesDataArg : ReadWriteProperty<Bundle, CoordinatesData?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): CoordinatesData? {
        return thisRef.getParcelable(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: CoordinatesData?) {
        thisRef.putParcelable(property.name, value)
    }
}
