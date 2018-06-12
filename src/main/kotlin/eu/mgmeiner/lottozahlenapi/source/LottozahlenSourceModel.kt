package eu.mgmeiner.lottozahlenapi.source

import java.time.LocalDate

data class LottozahlenSourceModel(
        val date: LocalDate,
        val super6: List<Int>,
        val spiel77: List<Int>,
        val lottozahlen6aus49SourceModel: Lottozahlen6aus49SourceModel
)

data class Lottozahlen6aus49SourceModel(
        val zahlen: List<Int>,
        val superzahl: Int
)