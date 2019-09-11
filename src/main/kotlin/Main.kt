import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        for(cours in Curso.listarCursos()) Curso(cours.key, cours.value)
//        for( rawCourse in Curso.listarCursos()) ppRawCourse(rawCourse.key, rawCourse.value)
    }
    println("Tempo total: $time")
}

fun ppRawCourse(nome: String, linkVersoes: MutableMap<String, String>) {
    println(nome)
    for(ver in linkVersoes) println("\t${ver.key}: ${ver.value}")
    println()
}


//https://www.siga.ufrj.br/sira/temas/zire/frameConsultas.jsp?mainPage=/repositorio-curriculo/FD213546-92A4-F799-634F-A707E3613326.html