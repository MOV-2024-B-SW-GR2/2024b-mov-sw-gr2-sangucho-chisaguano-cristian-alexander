package com.example.sistema_solar_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sistema_solar_crud.modelo.SistemaSolar

class SQLiteHelper(
    contexto: Context? // this
) : SQLiteOpenHelper(
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

        val scriptSQLCrearTablaPlaneta =
            """
                CREATE TABLE PLANETA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    descripcion VARCHAR(50),
                    monto Double,
                    cantidad INTEGER,
                    cliente_id INTEGER,
                    FOREIGN KEY(cliente_id) REFERENCES CLIENTE(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPlaneta)

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

    fun listarSistemasSolares(): ArrayList<SistemaSolar> {
        val scriptSQLConsultarSistemaSolar = "SELECT * FROM SISTEMASOLAR"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptSQLConsultarSistemaSolar,
            null
        )
        val sistemasSolares = ArrayList<SistemaSolar>()

        while (resultadoConsultaLectura.moveToNext()) {
            sistemasSolares.add(
                SistemaSolar(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getInt(3)
                )
            )
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sistemasSolares

    }

    fun eliminarSistemaSolar(id: Int) {
        val baseDatosEscritura = writableDatabase
        val scriptEliminarSistemaSolar = "DELETE FROM SISTEMASOLAR WHERE id = ?"
        val parametrosEliminar = arrayOf(id.toString())
        baseDatosEscritura.execSQL(scriptEliminarSistemaSolar, parametrosEliminar)
        baseDatosEscritura.close()

    }

    fun actualizarSistemaSolar(sistemaSolar: SistemaSolar) {
        val baseDatosEscritura = writableDatabase
        val scriptActualizarSistemaSolar =
            """
                UPDATE SISTEMASOLAR
                SET nombre = ?,
                    descripcion = ?,
                    tamanio = ?
                WHERE id = ?
            """.trimIndent()
        val parametrosActualizar = arrayOf(
            sistemaSolar.nombre,
            sistemaSolar.descripcion,
            sistemaSolar.tamanio.toString(),
            sistemaSolar.id.toString()
        )
        baseDatosEscritura.execSQL(scriptActualizarSistemaSolar, parametrosActualizar)
        baseDatosEscritura.close()

    }

}
