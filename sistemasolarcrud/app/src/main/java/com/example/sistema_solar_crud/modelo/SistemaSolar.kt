package com.example.sistema_solar_crud.modelo

import android.os.Parcel
import android.os.Parcelable

class SistemaSolar(
    var nombre: String,
    var descripcion: String?,
    var tamanio: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeInt(tamanio)
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