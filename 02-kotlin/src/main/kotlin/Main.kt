package org.example

import java.util.*

fun main(args: Array<String>) {
    //Variable inmutables
    var mutable: String = "Este texto puede cambiar";

    val inmutable: String = "Este texto no cambia";
    //inmutable = "Error";  // Esto causará un error

    //val > var
    val ejemploVariable = "Esto es un String";  // Kotlin deduce que es un String
    //Ducking Typing: If it walks like a duck and it quacks like a duck,
                    // then it must be a duck

    val string:String = "a";
    val double:Double = 2.35;
    val char:Char = 'b';
    val bool: Boolean = true;
    //Clases
    val date: Date = Date()

    val estadoCivil = "C"

    val resultado = when (estadoCivil) {
        "C" -> "Casado"
        "S" -> "Soltero"
        else -> "No sabemos"

    }

    val resultado2 = if (resultado == "Casado") "C" else "S"

    imprimirNombre("name");
    calcularSueldo(10.00, bonoEspecial = 20.00)
}

fun imprimirNombre(nombre: String): Unit {
    println("El nombre es: $nombre")                //sin llaves
    println("El nombre es: ${nombre}")              //llaves
    println("El nombre es: ${nombre + nombre}")     //llaves con concatenacion
    println("El nombre es: ${nombre.toString()}")   //llaves + funcion
    //println("El nombre es: $nombre.toString()")   //Incorrecto


}

fun calcularSueldo(
    sueldo:Double,
    tasa:Double = 12.00,
    bonoEspecial:Double? = null
):Double{
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) * bonoEspecial
    }
}


abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    parametroNoUsadoNoPropiedadDeLaClase:Int? = null
){
    init { // bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicite: String = "Publico implicito"
    init { // bloque constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL  [propiedades, metodos]
        numeroDos // this. OPCIONAL  [propiedades, metodos]
        this.soyPublicoImplicite
        soyPublicoExplicito
    }
    constructor( // Constructor secundario
        uno: Int?, // Entero nullable
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        // Bloque de codigo de constructor secundario
    }
    constructor( // Constructor secundario
        uno: Int,
        dos: Int?, // Entero nullable
    ):this(
        uno,
        if(dos == null) 0 else dos
    )
    constructor( // Constructor secundario
        uno: Int?, // Entero nullable
        dos: Int?,  // Entero nullable
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )
    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Comparte entre todas las instancias, similar al STATIC
        // funciones, variables
        // NombreClase.metodo, NombreClase.funcion =>
        // Suma.pi
        val pi = 3.14
        // Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num * num }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){ // Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }
}
