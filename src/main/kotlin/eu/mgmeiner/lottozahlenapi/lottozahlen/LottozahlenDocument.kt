package eu.mgmeiner.lottozahlenapi.lottozahlen

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "lottozahlen")
data class LottozahlenDocument (
        @Id
        val date: LocalDate,

        val saveDateTime: LocalDateTime = LocalDateTime.now(),

        val spiel77Zahlen: List<Int>,

        val super6Zahlen: List<Int>,

        val lotto6Aus49Document: Lotto6Aus49Document
)

data class Lotto6Aus49Document (
        val superzahl: Int,
        val zahlen: List<Int>
)