package com.example.gr2sw2024b_casc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mostrarSnackBar("OnCreate")


    }

    override fun onStart(){
        super.onStart()
        mostrarSnackBar("onStart")
    }
    override fun onResume(){
        super.onResume()
        mostrarSnackBar("onResume")
    }
    override fun onRestart() {
        super.onRestart()
        mostrarSnackBar("onRestart")
    }
    override fun onPause() {
        super.onPause()
        mostrarSnackBar("onPause")
    }
    override fun onStop() {
        super.onStop()
        mostrarSnackBar("onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackBar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            put("variableTextoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado: String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperado != null){
            mostrarSnackBar(textoRecuperado)
        }
    }

    var textoGlobal = ""
    fun mostrarSnackBar(texto: String){
        textoGlobal += texto
        var snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

}