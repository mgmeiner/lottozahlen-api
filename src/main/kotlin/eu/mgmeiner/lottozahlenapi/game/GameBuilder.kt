package eu.mgmeiner.lottozahlenapi.game

class GameBuilder private constructor() {
    private val gameTypes = mutableMapOf<LottoGameType, LottoGame>()

    fun withSuper6(nummer: Int) = also {
        gameTypes[LottoGameType.LOTTOSUPER6] = LottoSuper6Game(nummer)
    }

    fun withSpiel77(nummer: Int) = also {
        gameTypes[LottoGameType.LOTTOSPIEL77] = LottoSpiel77Game(nummer)
    }

    fun with6Aus49(nummern: List<Int>, superzahl: Int? = null, zusatzzahl: Int? = null) = also {
        gameTypes[LottoGameType.LOTTO6AUS49] = Lotto6Aus49Game(nummern, superzahl, zusatzzahl)
    }

    fun with6Aus49Ziehung2(nummern: List<Int>, superzahl: Int? = null, zusatzzahl: Int? = null) = also {
        gameTypes[LottoGameType.LOTTO6AUS49_ZIEHUNG2] = Lotto6Aus49Game(nummern, superzahl, zusatzzahl)
    }

    fun with7Aus38(nummern: List<Int>, zusatzzahl: Int) = also {
        gameTypes[LottoGameType.LOTTO7AUS38] = Lotto7Aus38Game(nummern, zusatzzahl)
    }

    fun build(): Map<LottoGameType, LottoGame> = gameTypes

    companion object {
        fun create() = GameBuilder()
    }
}