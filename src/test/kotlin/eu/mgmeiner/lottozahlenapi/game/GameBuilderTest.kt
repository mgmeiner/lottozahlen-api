package eu.mgmeiner.lottozahlenapi.game

import com.winterbe.expekt.should
import org.junit.jupiter.api.Test

class GameBuilderTest {

    @Test
    fun `test that GameBuilder builds games correct`() {
        val games = GameBuilder.create()
                .with6Aus49(listOf(1, 2, 3, 4, 5, 6), 1, 2)
                .with6Aus49Ziehung2(listOf(7, 8, 9, 10, 11, 12), 3, 4)
                .withSpiel77(77)
                .withSuper6(6)
                .build()

        games.should.have.size(4)

        games.should.contain(Pair(LottoGameType.LOTTO6AUS49, Lotto6Aus49Game(listOf(1, 2, 3, 4, 5, 6), 1, 2)))
        games.should.contain(Pair(LottoGameType.LOTTO6AUS49_ZIEHUNG2, Lotto6Aus49Game(listOf(7, 8, 9, 10, 11, 12), 3, 4)))
        games.should.contain(Pair(LottoGameType.LOTTOSPIEL77, LottoSpiel77Game(77)))
        games.should.contain(Pair(LottoGameType.LOTTOSUPER6, LottoSuper6Game(6)))
    }
}