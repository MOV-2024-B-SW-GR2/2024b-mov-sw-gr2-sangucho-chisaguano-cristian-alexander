package com.example.sistema_solar_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sistema_solar_crud.modelo.SistemaSolar
import com.google.android.material.snackbar.Snackbar

class CrearSistemaSolarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_sistema_solar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGuardarPlaneta = findViewById<Button>(R.id.btn_guardar_ss)
        val itNombre = findViewById<EditText>(R.id.it_nombre_ss)
        val itEdad = findViewById<EditText>(R.id.it_descripcion_ss)
        val itTamanio = findViewById<EditText>(R.id.it_tamanio_ss)

        btnGuardarPlaneta.setOnClickListener {
            val nombre = itNombre.text.toString()
            val edad = itEdad.text.toString()
            val tamanio = itTamanio.text.toString()

            if (nombre.isEmpty() || edad.isEmpty() || tamanio.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos")
            } else {
                BDSQLite.bdsqLite?.registrarSistemaSolar(
                    SistemaSolar(
                        nombre = nombre,
                        descripcion = edad,
                        tamanio = tamanio.toInt()
                    )
                )
                mostrarSnackbar("Sistema Solar guardado")
            }
        }



    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}