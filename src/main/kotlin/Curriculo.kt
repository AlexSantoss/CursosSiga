import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class Curriculo(val periodo: String, link: String){
    private val equivalencias: MutableMap<String, MutableList<String>> = mutableMapOf()
    private val grade: MutableMap<String, List<Materia>> = mutableMapOf()

    init { getData(link) }

    private fun getData(link: String) = with(Jsoup.connect(link).get()){
        Jsoup.connect(getElementById("frameDynamic").absUrl("src")).get().run {

            select("table.cellspacingTable").forEachIndexed { _, element ->
                when {
                    element.text().contains("Período") -> trataPeriodo(element)
                    element.text().contains("(Escolha Condicionada)") -> trataPeriodo(element)
                    element.text().contains("Equivalência") -> trataEquivalencia(element)
//                        else -> println(element.text())
                }
            }

//            grade.forEach { println("${it.key}\n${it.value.joinToString(separator = "\n")}\n") }
//            equivalencias.forEach { println("${it.key} = ${it.value}") }
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