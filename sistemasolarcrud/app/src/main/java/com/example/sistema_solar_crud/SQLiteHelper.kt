package com.example.sistema_solar_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sistema_solar_crud.modelo.Planeta
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
                    tamanio INTEGER,
                    latitud DOUBLE,
                    longitud DOUBLE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaSistemaSolar)

        val scriptSQLCrearTablaPlaneta =
            """
                CREATE TABLE PLANETA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    tipo VARCHAR(50),
                    distancia_al_sol INTEGER,
                    sistema_solar_id INTEGER,
                    FOREIGN KEY(sistema_solar_id) REFERENCES SISTEMASOLAR(id) ON DELETE CASCADE
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
        valoresGuardar.put("latitud", sistemaSolar.latitud)
        valoresGuardar.put("longitud", sistemaSolar.longitud)
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
                    resultadoConsultaLectura.getInt(3),
                    resultadoConsultaLectura.getDouble(4),
                    resultadoConsultaLectura.getDouble(5)
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
                    tamanio = ?,
                    latitud = ?,
                    longitud = ?
                WHERE id = ?
            """.trimIndent()
        val parametrosActualizar = arrayOf(
            sistemaSolar.nombre,
            sistemaSolar.descripcion,
            sistemaSolar.tamanio.toString(),
            sistemaSolar.latitud.toString().toDouble(),
            sistemaSolar.longitud.toString().toDouble(),
            sistemaSolar.id.toString()
        )
        baseDatosEscritura.execSQL(scriptActualizarSistemaSolar, parametrosActualizar)
        baseDatosEscritura.close()

    }


    fun crearPlaneta(planeta: Planeta): Boolean {
        val baseDatosEscritura = writableDatabase
        val scriptCrearPlaneta =
            """
            INSERT INTO PLANETA(nombre, tipo, distancia_al_sol, sistema_solar_id)
            VALUES(?, ?, ?, ?)
        """.trimIndent()
        val parametrosCrear = arrayOf(
            planeta.nombre,
            planeta.tipo,
            planeta.distancia.toString(),
            planeta.sistemaSolarId.toString()
        )
        return try {
            baseDatosEscritura.execSQL(scriptCrearPlaneta, parametrosCrear)
            baseDatosEscritura.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            baseDatosEscritura.close()
            false
        }
    }

    fun createPlanetaTableIfNotExists() {
        writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS PLANETA(\n" +
                "                    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                    nombre VARCHAR(50),\n" +
                "                    tipo VARCHAR(50),\n" +
                "                    distancia_al_sol INTEGER,\n" +
                "                    sistema_solar_id INTEGER,\n" +
                "                    FOREIGN KEY(sistema_solar_id) REFERENCES SISTEMASOLAR(id) ON DELETE CASCADE\n" +
                "                )")

    }

    fun listarPlanetas(sistemaSolarId: Int): ArrayList<Planeta> {
        val scriptSQLConsultarPlaneta = "SELECT * FROM PLANETA WHERE sistema_solar_id = ?"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptSQLConsultarPlaneta,
            arrayOf(sistemaSolarId.toString())
        )
        val planetas = ArrayList<Planeta>()

        while (resultadoConsultaLectura.moveToNext()) {
            planetas.add(
                Planeta(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getInt(3),
                    resultadoConsultaLectura.getInt(4)
                )
            )
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return planetas
    }

    fun eliminarPlaneta(planeta_id: Int, sistema_solar_id: Int) {
        val baseDatosEscritura = writableDatabase
        val scriptEliminarPlaneta = "DELETE FROM PLANETA WHERE id = ? AND sistema_solar_id = ?"
        val parametrosEliminar = arrayOf(planeta_id.toString(), sistema_solar_id.toString())
        baseDatosEscritura.execSQL(scriptEliminarPlaneta, parametrosEliminar)
        baseDatosEscritura.close()

    }

    fun actualizarPlaneta(planeta: Planeta): Boolean {
        val baseDatosEscritura = writableDatabase
        val scriptActualizarPlaneta =
            """
                UPDATE PLANETA
                SET nombre = ?,
                    tipo = ?,
                    distancia_al_sol = ?
                WHERE id = ?
            """.trimIndent()
        val parametrosActualizar = arrayOf(
            planeta.nombre,
            planeta.tipo,
            planeta.distancia.toString(),
            planeta.id.toString()
        )
        return try {
            baseDatosEscritura.execSQL(scriptActualizarPlaneta, parametrosActualizar)
            baseDatosEscritura.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            baseDatosEscritura.close()
            false
        }


    }


}
