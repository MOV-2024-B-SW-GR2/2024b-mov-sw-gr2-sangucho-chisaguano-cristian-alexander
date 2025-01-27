package com.example.sistema_solar_crud.modelo

import android.os.Parcel
import android.os.Parcelable

data class Planeta(
    val id: Int,
    val nombre: String,
    val tipo: String?,
    val distancia: Int,
    val sistemaSolarId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(tipo)
        parcel.writeInt(distancia)
        parcel.writeInt(sistemaSolarId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Planeta> {
        override fun createFromParcel(parcel: Parcel): Planeta {
            return Planeta(parcel)
        }

        override fun newArray(size: Int): Array<Planeta?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Planeta(id=$id, n:'$nombre', t:$tipo, d:$distancia, ssd:$sistemaSolarId)"

    }
}