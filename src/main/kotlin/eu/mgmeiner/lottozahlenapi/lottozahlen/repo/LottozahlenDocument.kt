package eu.mgmeiner.lottozahlenapi.lottozahlen.repo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document(collection = "lottozahlen")
data class LottozahlenDocument (
        @Id
        val date: Long,
        val saveDateTime: Long,
        val spiel77: Int,
        val super6: Int,
        val lotto6Aus49: Lotto6Aus49Document
)

data class Lotto6Aus49Document (
        val superzahl: Int,
        val zahlen: List<Int>
)