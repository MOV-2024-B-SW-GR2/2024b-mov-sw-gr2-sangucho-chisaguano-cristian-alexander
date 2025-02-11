package com.example.sistema_solar_crud.modelo

import android.os.Parcel
import android.os.Parcelable

class SistemaSolar(
    var id: Int = 0,
    var nombre: String,
    var descripcion: String?,
    var tamanio: Int,
    var latitud: Double,
    var longitud: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeInt(tamanio)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SistemaSolar> {
        override fun createFromParcel(parcel: Parcel): SistemaSolar {
            return SistemaSolar(parcel)
        }

        override fun newArray(size: Int): Array<SistemaSolar?> {
            return arrayOfNulls(size)
        }
    }
}