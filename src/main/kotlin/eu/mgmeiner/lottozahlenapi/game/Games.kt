package eu.mgmeiner.lottozahlenapi.game

interface LottoGame

data class Lotto6Aus49Game(
        val zahlen: List<Int>,
        val superzahl: Int?,
        val zusatzzahl: Int?
) : LottoGame

data class LottoSpiel77Game(
        val nummer: Int
) : LottoGame

data class LottoSuper6Game(
        val nummer: Int
) : LottoGame

enum class LottoGameType {
    LOTTO6AUS49 {
        override fun toString() = "lotto6Aus49"
    },
    LOTTO6AUS49_ZIEHUNG2 {
        override fun toString() = "lotto6Aus49Ziehung2"
    },
    LOTTOSPIEL77 {
        override fun toString() = "lottoSpiel77"
    },
    LOTTOSUPER6 {
        override fun toString() = "lottoSuper6"
    };

    abstract override fun toString(): String
}