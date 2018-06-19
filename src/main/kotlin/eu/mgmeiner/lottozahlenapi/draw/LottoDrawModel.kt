package eu.mgmeiner.lottozahlenapi.draw

import eu.mgmeiner.lottozahlenapi.game.LottoGame
import eu.mgmeiner.lottozahlenapi.game.LottoGameType
import java.time.LocalDate

data class LottoDrawModel(
        val date: LocalDate,
        val games: Map<LottoGameType, LottoGame>
)