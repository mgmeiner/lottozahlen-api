package eu.mgmeiner.lottozahlenapi.game

enum class LottoGameType {
    LOTTO6AUS49 {
        override fun toString() = "lotto6Aus49"
    },
    LOTTO6AUS49_ZIEHUNG2 {
        override fun toString() = "lotto6Aus49Ziehung2"
    },
    LOTTO7AUS38 {
        override fun toString() = "lotto7Aus38"
    },
    LOTTOSPIEL77 {
        override fun toString() = "lottoSpiel77"
    },
    LOTTOSUPER6 {
        override fun toString() = "lottoSuper6"
    },
    UNKNOWN {
        override fun toString() = "unknown"
    };

    abstract override fun toString(): String

    companion object {
        fun parse(source: String) = when (source) {
            "lotto6Aus49" -> LottoGameType.LOTTO6AUS49
            "lotto6Aus49Ziehung2" -> LottoGameType.LOTTO6AUS49_ZIEHUNG2
            "lotto7Aus38" -> LottoGameType.LOTTO7AUS38
            "lottoSpiel77" -> LottoGameType.LOTTOSPIEL77
            "lottoSuper6" -> LottoGameType.LOTTOSUPER6
            else -> LottoGameType.UNKNOWN
        }
    }
}