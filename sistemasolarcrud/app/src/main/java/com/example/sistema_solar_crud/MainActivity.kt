package com.example.sistema_solar_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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
        val opcion: Int = item.getItemId();
        when (opcion) {
            R.id.m_eliminar_ss -> {}
            R.id.m_eliminar_ss -> {}
            R.id.m_ver_ss -> {}
        }
        return super.onContextItemSelected(item)

    }


}

