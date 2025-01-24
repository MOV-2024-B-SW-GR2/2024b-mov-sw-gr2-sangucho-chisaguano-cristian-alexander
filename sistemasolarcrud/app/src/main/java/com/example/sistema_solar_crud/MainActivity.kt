package com.example.sistema_solar_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sistema_solar_crud.modelo.SistemaSolar

class MainActivity : AppCompatActivity() {
    private lateinit var sistemasSolares: ArrayList<SistemaSolar>
    private lateinit var btnAgregarSistemaSolar: Button
    private lateinit var listViewSistemaSolar: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inicializar bd
        BDSQLite.bdsqLite = SQLiteHelper(this)




        listViewSistemaSolar = findViewById(R.id.ls_sistemas_solares)
        btnAgregarSistemaSolar = findViewById(R.id.btn_crear_ss)

        registerForContextMenu(listViewSistemaSolar)

        btnAgregarSistemaSolar.setOnClickListener {
                startActivity(Intent(this, CrearSistemaSolarActivity::class.java))
        }

        //mostrar-actualizar la lista
        actualizarLista()

    }

    private fun actualizarLista() {
        sistemasSolares = BDSQLite.bdsqLite?.listarSistemasSolares() ?: ArrayList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sistemasSolares.map { it.nombre }
        )
        listViewSistemaSolar.adapter = adapter
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_ss, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
        val sistemaSolarIndex = info?.position
        val sistemaSolarSeleccionado = sistemaSolarIndex?.let { sistemasSolares[it] }

        when (item.itemId) {
            R.id.m_eliminar_ss -> {
                sistemaSolarSeleccionado?.let {
                    BDSQLite.bdsqLite?.eliminarSistemaSolar(it.id)
                    actualizarLista()
                }
            }
            R.id.m_ver_ss -> {
                sistemaSolarSeleccionado?.let {
                    val intent = Intent(this, PlanetaActivity::class.java)
                    intent.putExtra("sistemaSolarId", it.id)
                    startActivity(intent)
                }
            }
            R.id.m_editar_ss -> {
                sistemaSolarSeleccionado?.let {
                    val intent = Intent(this, CrearSistemaSolarActivity::class.java)
                    intent.putExtra("sistemaSolarId", it.id)
                    intent.putExtra("nombre", it.nombre)
                    intent.putExtra("descripcion", it.descripcion)
                    intent.putExtra("tamanio", it.tamanio)
                    startActivity(intent)
                }
            }
        }
        return super.onContextItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}

