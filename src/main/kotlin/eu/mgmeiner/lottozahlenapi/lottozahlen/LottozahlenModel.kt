package eu.mgmeiner.lottozahlenapi.lottozahlen

import java.time.LocalDate

data class LottozahlenModel (
        val date: LocalDate,
        val spiel77: Int?,
        val super6: Int?,
        val lotto6Aus49: Lotto6Aus49Model
)

data class Lotto6Aus49Model (
        val superzahl: Int?,
        val zahlen: List<Int>
)