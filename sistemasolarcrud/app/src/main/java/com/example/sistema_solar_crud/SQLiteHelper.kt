package com.example.sistema_solar_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sistema_solar_crud.modelo.SistemaSolar

class SQLiteHelper(
    contexto: Context? // this
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaSistemaSolar =
            """
                CREATE TABLE SISTEMASOLAR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50),
                    tamanio INTEGER
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaSistemaSolar)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun registrarSistemaSolar(
        sistemaSolar: SistemaSolar
    ): Boolean {
        val baseDatosEscritura = writableDatabase

        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", sistemaSolar.nombre)
        valoresGuardar.put("descripcion", sistemaSolar.descripcion)
        valoresGuardar.put("tamanio", sistemaSolar.tamanio)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "SISTEMASOLAR", // nombre tabla
                null,
                valoresGuardar // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

}
