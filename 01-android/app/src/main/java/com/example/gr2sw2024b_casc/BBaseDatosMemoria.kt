package com.example.gr2sw2024b_casc

class BBaseDeDatosMemoria {

    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "a", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(1, "b", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(1, "c", "a@a.com"))
        }
    }
}