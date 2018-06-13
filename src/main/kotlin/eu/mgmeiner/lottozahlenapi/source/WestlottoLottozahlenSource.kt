package eu.mgmeiner.lottozahlenapi.source

import eu.mgmeiner.lottozahlenapi.config.LottozahlenAPIConfigProps
import eu.mgmeiner.lottozahlenapi.lottozahlen.Lotto6Aus49Document
import eu.mgmeiner.lottozahlenapi.lottozahlen.LottozahlenDocument
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

@Service
class WestlottoLottozahlenSource(private val configProps: LottozahlenAPIConfigProps) : LottozahlenSource {

    override fun getCurrentLottozahlen(): Mono<LottozahlenDocument> = WebClient
            .create(configProps.westlottoRSSFeedUrl)
            .get()
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { parseXml(it) }

    fun parseXml(xml: String): LottozahlenDocument {
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
                lotto6Aus49Raw = getTitleElementFromItem(0),
                lottoSpiel77Raw = getTitleElementFromItem(1),
                lottoSuper6Raw = getTitleElementFromItem(2)
        )
    }

    fun createLottozahlenSourceModelFromRawValues(lotto6Aus49Raw: String, lottoSpiel77Raw: String, lottoSuper6Raw: String) = LottozahlenDocument(
            date = getDateFromRawString(lotto6Aus49Raw),
            super6 = getSuper6NumbersFromRawString(lottoSuper6Raw),
            spiel77 = getSpiel77NumbersFromRawString(lottoSpiel77Raw),
            lotto6Aus49 = Lotto6Aus49Document(
                    superzahl = getSuperzahlFromRawString(lotto6Aus49Raw),
                    zahlen = get6Aus49NumbersFromRawString(lotto6Aus49Raw)
            )
    )

    fun getSuper6NumbersFromRawString(lottoSuper6Raw: String) =
            ("\\d{6}".toRegex()
                    .find(lottoSuper6Raw)?.value
                    ?: throw LottozahlenSourceException("No 'Super6 Zahlen' in $lottoSuper6Raw"))
                    .toInt()

    fun getSpiel77NumbersFromRawString(lottoSpiel77Raw: String) =
            ("\\d{7}".toRegex()
                    .find(lottoSpiel77Raw)?.value
                    ?: throw LottozahlenSourceException("No 'Spiel77 Zahlen' in $lottoSpiel77Raw"))
                    .toInt()

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