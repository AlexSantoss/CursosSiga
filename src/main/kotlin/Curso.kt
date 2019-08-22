import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.system.measureTimeMillis

fun listarCursos()
        = with(Jsoup.connect("https://www.siga.ufrj.br/sira/repositorio-curriculo/80167CF7-3880-478C-8293-8E7D80CEDEBE.html").get()){
    select("tr.tableTitleBlue").forEach {
        println(this.body())
        println("----------")
    }
}

class Curso(id: String) {
    companion object{
        const val LINK = "https://www.siga.ufrj.br/sira/repositorio-curriculo/$$$.html"
    }

    private val link: String
    private val equivalencias: MutableMap<String, MutableList<String>>
    private val grade: MutableMap<String, List<Materia>>

    init {
        link = LINK.replace("$$$", id)
        equivalencias = mutableMapOf()
        grade = mutableMapOf()
        getData()
    }

    private fun getData() = with(Jsoup.connect(link).get()){
        Jsoup.connect(getElementById("frameDynamic").absUrl("src")).get().run {
            val time = measureTimeMillis {
                select("table.cellspacingTable").forEachIndexed { _, element ->
                    when {
                        element.text().contains("Período") -> trataPeriodo(element)
                        element.text().contains("(Escolha Condicionada)") -> trataPeriodo(element)
                        element.text().contains("Equivalência") -> trataEquivalencia(element)
//                        else -> println(element.text())
                    }
                }
            }
            println("Tempo no parse: $time")
            grade.forEach { println("${it.key}\n${it.value.joinToString(separator = "\n")}\n") }
            equivalencias.forEach { println("${it.key} = ${it.value}") }
        }
    }

    private fun trataEquivalencia(element: Element?) = element?.run{
        var ult = ""
        select("[class*=tableBody]").forEach {el ->
            val temp = el.select("td")

            val cod1 = temp[1].text().extractCode().ifEmpty { ult }
            val cod2 = temp[3].text().split(" + ").joinToString(separator = "+") { it.extractCode() }

            equivalencias.putIfAbsent(cod1, mutableListOf())
            equivalencias[cod1]!! += cod2

            ult = cod1
        }
    }

    private fun String.extractCode() = split(" ")[0]

    private fun trataPeriodo(element: Element?) = element?.run {
        val periodo = select("tr.tableTitle").text()
        val materias = select("[class*=tableBody]").mapNotNull { Materia.fromElement(it) }

        grade[periodo] = materias
    }
}