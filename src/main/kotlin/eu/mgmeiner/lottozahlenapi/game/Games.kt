package eu.mgmeiner.lottozahlenapi.game

interface LottoGame

data class Lotto6Aus49Game(
        val zahlen: List<Int>,
        val superzahl: Int?,
        val zusatzzahl: Int?
) : LottoGame

data class Lotto7Aus38Game(
        val zahlen: List<Int>,
        val zusatzzahl: Int
) : LottoGame

data class LottoSpiel77Game(
        val nummer: Int
) : LottoGame

data class LottoSuper6Game(
        val nummer: Int
) : LottoGame