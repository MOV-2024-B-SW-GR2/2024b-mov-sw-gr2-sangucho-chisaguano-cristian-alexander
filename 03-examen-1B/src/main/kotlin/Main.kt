import java.io.*
import java.time.LocalDate
import java.util.*

class SistemaSolar(
    val id: Int,
    var nombre: String,
    var añoDescubrimiento: Int,
    var numeroDePlanetas: Int,
    var planetas: ArrayList<Planeta> = ArrayList()
) {
    override fun toString(): String {
        return "$id|$nombre|$añoDescubrimiento|$numeroDePlanetas"
    }
    fun mostrarPlanetas() {
        println("Sistema Solar: $nombre tiene ${planetas.size} planetas:")
        planetas.forEach { println(it) }
    }
}

class Planeta(
    val id: Int,
    var nombre: String,
    var tipo: String,
    var tieneAnillos: Boolean,
    var distanciaAlSol: Double,
    var fechaDescubrimiento: LocalDate
) {
    override fun toString(): String {
        return "$id|$nombre|$tipo|$tieneAnillos|$distanciaAlSol|$fechaDescubrimiento"
    }
}

class SistemaSolarManager {
    private val sistemas = ArrayList<SistemaSolar>()
    private val planetas = ArrayList<Planeta>()
    private var ultimoIdSistemaSolar = 0
    private var ultimoIdPlaneta = 0
    private val archivoSistemas = "sistemas.txt"
    private val archivoPlanetas = "planetas.txt"
    private val archivoRelaciones = "relaciones.txt"

    init {
        inicializarSistema()
    }

    private fun inicializarSistema() {
        if (File(archivoSistemas).readText().isBlank() || File(archivoPlanetas).readText().isBlank()) {
            println("Archivos vacíos. Generando datos por defecto...")
            generarDatosPorDefecto()
        } else {
            println("Archivos con datos existentes. Cargando...")
            cargarDatos()
        }
    }

    private fun generarDatosPorDefecto() {
        val sistema1 = SistemaSolar(1, "Sistema Solar", 1800, 8)
        sistemas.add(sistema1)
        repeat(8) { i ->
            val planeta = Planeta(
                id = ++ultimoIdPlaneta,
                nombre = "Planeta ${i + 1}",
                tipo = if (i % 2 == 0) "Terrestre" else "Gaseoso",
                tieneAnillos = i % 2 == 0,
                distanciaAlSol = (i + 1) * 50.0,
                fechaDescubrimiento = LocalDate.of(1600, 1, 1)
            )
            planetas.add(planeta)
            sistema1.planetas.add(planeta)
        }
        guardarDatos()
    }

    private fun cargarDatos() {
        // Cargar planetas
        if (File(archivoPlanetas).exists()) {
            File(archivoPlanetas).forEachLine { linea ->
                val datos = linea.split("|")
                val planeta = Planeta(
                    datos[0].toInt(),
                    datos[1],
                    datos[2],
                    datos[3].toBoolean(),
                    datos[4].toDouble(),
                    LocalDate.parse(datos[5])
                )
                planetas.add(planeta)
                if (planeta.id > ultimoIdPlaneta) ultimoIdPlaneta = planeta.id
            }
        }

        // Cargar sistemas solares
        if (File(archivoSistemas).exists()) {
            File(archivoSistemas).forEachLine { linea ->
                val datos = linea.split("|")
                val sistema = SistemaSolar(
                    datos[0].toInt(),
                    datos[1],
                    datos[2].toInt(),
                    datos[3].toInt()
                )
                sistemas.add(sistema)
                if (sistema.id > ultimoIdSistemaSolar) ultimoIdSistemaSolar = sistema.id
            }
        }

        // Cargar relaciones
        if (File(archivoRelaciones).exists()) {
            File(archivoRelaciones).forEachLine { linea ->
                val datos = linea.split("|")
                val sistemaId = datos[0].toInt()
                val planetaId = datos[1].toInt()
                val sistema = sistemas.find { it.id == sistemaId }
                val planeta = planetas.find { it.id == planetaId }
                if (sistema != null && planeta != null) {
                    sistema.planetas.add(planeta)
                }
            }
        }
    }

    private fun guardarDatos() {
        // Guardar sistemas solares en archivo
        File(archivoSistemas).printWriter().use { out ->
            sistemas.forEach { out.println(it.toString()) }
        }

        // Guardar planetas en archivo
        File(archivoPlanetas).printWriter().use { out ->
            planetas.forEach { out.println(it.toString()) }
        }

        // Guardar relaciones en archivo
        File(archivoRelaciones).printWriter().use { out ->
            sistemas.forEach { sistema ->
                sistema.planetas.forEach { planeta ->
                    out.println("${sistema.id}|${planeta.id}")
                }
            }
        }
    }

    fun mostrarTodosLosSistemas() {
        println("Lista de todos los sistemas solares:")
        sistemas.forEach { println(it) }
    }

    fun mostrarTodosLosPlanetas() {
        println("Lista de todos los planetas:")
        planetas.forEach { println(it) }
    }

    // CRUD SistemaSolar
    fun crearSistemaSolar(nombre: String, añoDescubrimiento: Int): Int {
        val id = ++ultimoIdSistemaSolar
        val sistema = SistemaSolar(id, nombre, añoDescubrimiento, 0)
        sistemas.add(sistema)
        guardarDatos()
        return id
    }

    fun obtenerSistemaSolar(id: Int): SistemaSolar? {
        return sistemas.find { it.id == id }
    }

    fun actualizarSistemaSolar(id: Int, nombre: String, añoDescubrimiento: Int): Boolean {
        val sistema = obtenerSistemaSolar(id)
        if (sistema != null) {
            sistema.nombre = nombre
            sistema.añoDescubrimiento = añoDescubrimiento
            guardarDatos()
            return true
        }
        return false
    }

    fun eliminarSistemaSolar(id: Int): Boolean {
        val sistema = obtenerSistemaSolar(id)
        if (sistema != null) {
            sistemas.remove(sistema)
            sistema.planetas.clear() // Limpiar planetas asociados
            guardarDatos()
            return true
        }
        return false
    }


    // CRUD Planeta
    fun crearPlaneta(nombre: String, tipo: String, tieneAnillos: Boolean, distanciaAlSol: Double, fechaDescubrimiento: LocalDate): Int {
        val id = ++ultimoIdPlaneta
        val planeta = Planeta(id, nombre, tipo, tieneAnillos, distanciaAlSol, fechaDescubrimiento)
        planetas.add(planeta)
        guardarDatos()
        return id
    }

    fun obtenerPlaneta(id: Int): Planeta? {
        return planetas.find { it.id == id }
    }

    fun actualizarPlaneta(id: Int, nombre: String, tipo: String, tieneAnillos: Boolean, distanciaAlSol: Double, fechaDescubrimiento: LocalDate): Boolean {
        val planeta = obtenerPlaneta(id)
        if (planeta != null) {
            planeta.nombre = nombre
            planeta.tipo = tipo
            planeta.tieneAnillos = tieneAnillos
            planeta.distanciaAlSol = distanciaAlSol
            planeta.fechaDescubrimiento = fechaDescubrimiento
            guardarDatos()
            return true
        }
        return false
    }

    fun eliminarPlaneta(id: Int): Boolean {
        val resultado = planetas.removeIf { it.id == id }
        if (resultado) {
            // Eliminar planeta de sistemas solares
            sistemas.forEach { sistema ->
                sistema.planetas.removeIf { it.id == id }
            }
            guardarDatos()
        }
        return resultado
    }

    fun agregarPlanetaASistema(sistemaId: Int, planetaId: Int): Boolean {
        val sistema = obtenerSistemaSolar(sistemaId)
        val planeta = obtenerPlaneta(planetaId)
        if (sistema != null && planeta != null) {
            // Verificar si el planeta ya está en el sistema
            if (sistema.planetas.any { it.id == planetaId }) {
                println("El planeta ya está agregado al sistema solar.")
                return false
            }
            sistema.planetas.add(planeta)
            sistema.numeroDePlanetas = sistema.planetas.size // Actualizar número de planetas
            guardarDatos()
            return true
        }
        return false
    }

}

fun main() {
    val sistemaSolarManager = SistemaSolarManager()
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\n########## SISTEMA SOLAR ##########")
        println("1. Sistemas Solares")
        println("2. Planetas")
        println("0. Salir")
        print("Seleccione una opción: ")

        when (scanner.nextLine()) {
            "1" -> {
                println("\n########## GESTIÓN DE SISTEMAS SOLARES ##########")
                println("1. Crear Sistema Solar")
                println("2. Ver Sistema Solar")
                println("3. Actualizar Sistema Solar")
                println("4. Eliminar Sistema Solar")
                println("5. Ver todos los Sistemas Solares")
                println("6. Agregar Planeta a Sistema Solar")
                print("Seleccione una opción: ")

                when (scanner.nextLine()) {
                    "1" -> {
                        print("Nombre: ")
                        val nombre = scanner.nextLine()
                        print("Año de descubrimiento: ")
                        val año = scanner.nextLine().toInt()
                        val id = sistemaSolarManager.crearSistemaSolar(nombre, año)
                        println("Sistema Solar creado con ID: $id")
                    }

                    "2" -> {
                        print("ID del sistema solar: ")
                        val sistema = sistemaSolarManager.obtenerSistemaSolar(scanner.nextLine().toInt())
                        if (sistema != null) {
                            sistema.mostrarPlanetas()
                        } else {
                            println("Sistema solar no encontrado")
                        }
                    }

                    "3" -> {
                        print("ID del sistema solar: ")
                        val id = scanner.nextLine().toInt()
                        print("Nuevo nombre: ")
                        val nombre = scanner.nextLine()
                        print("Nuevo año de descubrimiento: ")
                        val año = scanner.nextLine().toInt()
                        if (sistemaSolarManager.actualizarSistemaSolar(id, nombre, año)) {
                            println("Sistema Solar actualizado")
                        } else {
                            println("Sistema Solar no encontrado")
                        }
                    }

                    "4" -> {
                        print("ID del sistema : ")
                        if (sistemaSolarManager.eliminarSistemaSolar(scanner.nextLine().toInt())) {
                            println("Sistema solar eliminado")
                        } else {
                            println("Sistema solar no encontrado")
                        }
                    }

                    "5" -> {
                        sistemaSolarManager.mostrarTodosLosSistemas()
                    }

                    "6" -> {
                        print("ID del sistema solar: ")
                        val sistemaId = scanner.nextLine().toInt()
                        print("ID del planeta: ")
                        val planetaId = scanner.nextLine().toInt()
                        if (sistemaSolarManager.agregarPlanetaASistema(sistemaId, planetaId)) {
                            println("Planeta agregado al sistema solar")
                        } else {
                            println("Sistema solar o planeta no encontrado")
                        }
                    }
                }
            }

            "2" -> {
                println("\n########## GESTIÓN DE PLANETAS ##########")
                println("1. Crear Planeta")
                println("2. Ver Planeta")
                println("3. Actualizar Planeta")
                println("4. Eliminar Planeta")
                println("5. Ver todos los Planetas")
                print("Seleccione una opción: ")

                when (scanner.nextLine()) {
                    "1" -> {
                        print("Nombre: ")
                        val nombre = scanner.nextLine()
                        print("Tipo (Terrestre/Gaseoso): ")
                        val tipo = scanner.nextLine()
                        print("Tiene anillos (true/false): ")
                        val tieneAnillos = scanner.nextLine().toBoolean()
                        print("Distancia al Sol (en millones de kilómetros): ")
                        val distancia = scanner.nextLine().toDouble()
                        print("Fecha de descubrimiento (yyyy-MM-dd): ")
                        val fecha = LocalDate.parse(scanner.nextLine())
                        val id = sistemaSolarManager.crearPlaneta(nombre, tipo, tieneAnillos, distancia, fecha)
                        println("Planeta creado con ID: $id")
                    }

                    "2" -> {
                        print("ID del planeta: ")
                        val planeta = sistemaSolarManager.obtenerPlaneta(scanner.nextLine().toInt())
                        if (planeta != null) {
                            println(planeta)
                        } else {
                            println("Planeta no encontrado")
                        }
                    }

                    "3" -> {
                        print("ID del planeta: ")
                        val id = scanner.nextLine().toInt()
                        print("Nuevo nombre: ")
                        val nombre = scanner.nextLine()
                        print("Nuevo tipo (Terrestre/Gaseoso): ")
                        val tipo = scanner.nextLine()
                        print("Nuevo estado de anillos (true/false): ")
                        val tieneAnillos = scanner.nextLine().toBoolean()
                        print("Nueva distancia al Sol: ")
                        val distancia = scanner.nextLine().toDouble()
                        print("Nueva fecha de descubrimiento: ")
                        val fecha = LocalDate.parse(scanner.nextLine())
                        if (sistemaSolarManager.actualizarPlaneta(id, nombre, tipo, tieneAnillos, distancia, fecha)) {
                            println("Planeta actualizado")
                        } else {
                            println("Planeta no encontrado")
                        }
                    }

                    "4" -> {
                        print("ID del planeta: ")
                        if (sistemaSolarManager.eliminarPlaneta(scanner.nextLine().toInt())) {
                            println("Planeta eliminado")
                        } else {
                            println("Planeta no encontrado")
                        }
                    }

                    "5" -> {
                        sistemaSolarManager.mostrarTodosLosPlanetas()
                    }
                }
            }

            "0" -> break
            else -> println("Opción no válida")
        }
    }
}
