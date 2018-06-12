package eu.mgmeiner.lottozahlenapi.source

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

@Service
class WestlottoLottozahlenSource : LottozahlenSource {

    override fun getCurrentLottozahlen(): Mono<LottozahlenSourceModel> = WebClient
            .create("http://www.ergebnisse.westlotto.de/gewinnzahlen/lottozahlen.source")
            .get()
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { parseXml(it) }

    fun parseXml(xml: String): LottozahlenSourceModel {
        val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml.byteInputStream())

        xmlDoc.normalizeDocument()

        fun getTitleElementFromItem(itemIndex: Int): String {
            val childNodes = xmlDoc.getElementsByTagName("item").item(itemIndex).childNodes

            for (i in 0 until childNodes.length - 1) {
                val childNode = childNodes.item(i)

                if (childNode.nodeName == "title") {
                    return childNode.textContent
                }
            }

            throw LottozahlenSourceException("no title element found in document on item with index  $itemIndex")
        }

        return createLottozahlenSourceModelFromRawValues(
                lottoSuper6Raw = getTitleElementFromItem(0),
                lottoSpiel77Raw = getTitleElementFromItem(1),
                lotto6Aus49Raw = getTitleElementFromItem(2)
        )
    }

    fun createLottozahlenSourceModelFromRawValues(lottoSuper6Raw: String, lottoSpiel77Raw: String, lotto6Aus49Raw: String): LottozahlenSourceModel {
        return LottozahlenSourceModel(
                date = getDateFromRawString(lotto6Aus49Raw),
                super6 = getSuper6NumbersFromRawString(lottoSuper6Raw),
                spiel77 = getSpiel77NumbersFromRawString(lottoSpiel77Raw),
                lottozahlen6aus49SourceModel = Lottozahlen6aus49SourceModel(
                        get6Aus49NumbersFromRawString(lotto6Aus49Raw),
                        getSuperzahlFromRawString(lotto6Aus49Raw))
        )
    }

    fun getSuper6NumbersFromRawString(lottoSuper6Raw: String) =
            ("\\d{6}".toRegex()
                    .find(lottoSuper6Raw)?.value
                    ?: throw LottozahlenSourceException("No 'Super6 Zahlen' in $lottoSuper6Raw"))
                    .split("").filter { it.isNotEmpty() }.map { it.toInt() }

    fun getSpiel77NumbersFromRawString(lottoSpiel77Raw: String) =
            ("\\d{7}".toRegex()
                    .find(lottoSpiel77Raw)?.value
                    ?: throw LottozahlenSourceException("No 'Spiel77 Zahlen' in $lottoSpiel77Raw"))
                    .split("").filter { it.isNotEmpty() }.map { it.toInt() }

    fun getDateFromRawString(lotto6Aus49Raw: String): LocalDate =
            LocalDate.parse(
                    "\\d{2}\\.\\d{2}\\.\\d{2}".toRegex().find(lotto6Aus49Raw)?.value
                            ?: throw LottozahlenSourceException("No Date found in $lotto6Aus49Raw"),
                    DateTimeFormatter.ofPattern("dd.MM.yy")
            )

    fun getSuperzahlFromRawString(lotto6Aus49Raw: String) =
            ("(S:\\s)(\\d)".toRegex().find(lotto6Aus49Raw)
                    ?: throw LottozahlenSourceException("No 'Superzahl' found in $lotto6Aus49Raw")).groupValues[2].toInt()

    fun get6Aus49NumbersFromRawString(lotto6Aus49Raw: String): List<Int> {
        val lotto6Aus49OnlyNumbersRaw = "\\d*, \\d*, \\d*, \\d*, \\d*, \\d*".toRegex().find(lotto6Aus49Raw)?.value
                ?: throw LottozahlenSourceException("No '6 aus 49 Zahlen' in $lotto6Aus49Raw")

        return "\\d+".toRegex().findAll(lotto6Aus49OnlyNumbersRaw).map { it.value.toInt() }.toList()
    }
}