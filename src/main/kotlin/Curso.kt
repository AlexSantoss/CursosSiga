import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.system.measureTimeMillis

class Curso(val nome: String, linkVersoes: MutableMap<String, String>) {
    val versoesCurriculares = mutableMapOf<String, Curriculo>()
    init { for(id in linkVersoes) versoesCurriculares[id.key] = Curriculo(id.key, LINK + id.value ) }

    companion object{
        const val LINK = "https://www.siga.ufrj.br"
        const val SIGA_GRADUACAO = "https://www.siga.ufrj.br/sira/repositorio-curriculo/80167CF7-3880-478C-8293-8E7D80CEDEBE.html"

        fun listarCursos() = Jsoup.connect(SIGA_GRADUACAO).get().run {
            val all = mutableMapOf<String, MutableMap<String, String>>()
            select("tr.tableTitleBlue, tr.tableBodyBlue2").forEach {courseHtml ->
                val courseName = courseHtml.select("b").text()
                val versoes = mutableMapOf<String, String>()

                courseHtml.select("a.linkNormal").forEach {
                    val link = it.attr("href")
                        .replace("temas/zire/frameConsultas.jsp?mainPage=/", "")
                        .replace("javascript:Ementa('", "")
                        .replace("')", "")

                    if(link != "") versoes[it.text()] = link
                }
                all[courseName] = versoes
            }
            all
        }
    }
}