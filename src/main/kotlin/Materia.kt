import org.jsoup.nodes.Element

class Materia (val cod: String = "", val nome: String, val credito: Double, val requisitos: List<String> = listOf()) {
    companion object {
        fun fromElement(el: Element?): Materia? = el?.select("td")?.run{
            when (size) {
                7 -> Materia(get(0).text(), get(1).text(), get(2).text().toDouble(), trataRequisitos(get(6).text()))
                6 -> Materia(nome = get(0).text(), credito = get(1).text().toDouble())
                else -> null
            }
        }

        private fun trataRequisitos(req: String): List<String> {
            var split = req.split(' ')
            val equalsIdx = split.indexOf("=")

            if(equalsIdx != -1) split = split.subList(0, equalsIdx-1)
            return split.filter { !it.contains("(P)") && !it.contains("(C)") }
        }
    }

    override fun toString() = "$cod $nome $credito $requisitos"
}