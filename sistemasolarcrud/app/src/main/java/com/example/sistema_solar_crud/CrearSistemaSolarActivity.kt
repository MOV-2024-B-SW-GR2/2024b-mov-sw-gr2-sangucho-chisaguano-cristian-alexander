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
    private var sistemaSolarId: Int? = null

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
        val itDescripcion = findViewById<EditText>(R.id.it_descripcion_ss)
        val itTamanio = findViewById<EditText>(R.id.it_tamanio_ss)

        sistemaSolarId = intent.getIntExtra("sistemaSolarId", -1)
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val tamanio = intent.getIntExtra("tamanio", -1)
        if (sistemaSolarId != -1 && nombre != null && descripcion != null && tamanio != -1) {
            itNombre.setText(nombre)
            itDescripcion.setText(descripcion)
            itTamanio.setText(tamanio.toString())
        }


        btnGuardarPlaneta.setOnClickListener {
            val nuevoNombre = itNombre.text.toString()
            val nuevaDescripcion = itDescripcion.text.toString()
            val nuevoTamanio = itTamanio.text.toString()

            if (nuevoNombre.isEmpty() || nuevaDescripcion.isEmpty() || nuevoTamanio.isEmpty()) {
                mostrarSnackbar("Por favor, llene todos los campos")
            } else {
                val sistemaSolar = SistemaSolar(
                    id = sistemaSolarId ?: 0,
                    nombre = nuevoNombre,
                    descripcion = nuevaDescripcion,
                    tamanio = nuevoTamanio.toInt()
                )

                if (sistemaSolarId != -1) {
                    // Actualizar sistema solar existente
                    BDSQLite.bdsqLite?.actualizarSistemaSolar(sistemaSolar)
                    mostrarSnackbar("Sistema Solar actualizado")
                } else {
                    // Registrar nuevo sistema solar
                    BDSQLite.bdsqLite?.registrarSistemaSolar(sistemaSolar)
                    mostrarSnackbar("Sistema Solar guardado")
                }
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