import org.jsoup.nodes.Element

class Materia (val cod: String, val nome: String, val credito: Double, val requisitos: List<String>) {
    companion object {
        fun fromElement(el: Element?): Materia? = with(el?.select("td")){
            if(this != null && size == 7)
                Materia(get(0).text(), get(1).text(), get(2).text().toDouble(), trataRequisitos(get(6).text()))
            else
                null
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