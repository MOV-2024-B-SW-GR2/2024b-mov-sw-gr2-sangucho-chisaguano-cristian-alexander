package com.example.gr2sw2024b_vaes

class BSistemaSolar(
    var id: Int,
    var nombre: String,
    var num_de_planetas: Int?,
    var tamanio: Int?
) {
    override fun toString(): String {
        return "$id $nombre $num_de_planetas $tamanio"
    }
}