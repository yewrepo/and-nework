package ru.netology.nework.app.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

data class UserData(
    val authorId: Long,
    val author: String,
    val authorAvatar: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(authorId)
        parcel.writeString(author)
        parcel.writeString(authorAvatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}

object UserDataArg : ReadWriteProperty<Bundle, UserData?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): UserData? {
        return thisRef.getParcelable(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: UserData?) {
        thisRef.putParcelable(property.name, value)
    }
}
