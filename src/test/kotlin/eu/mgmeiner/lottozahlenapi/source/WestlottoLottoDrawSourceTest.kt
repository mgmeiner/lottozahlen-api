package eu.mgmeiner.lottozahlenapi.source

import com.winterbe.expekt.should
import eu.mgmeiner.lottozahlenapi.config.LottozahlenAPIConfigProps
import eu.mgmeiner.lottozahlenapi.game.Lotto6Aus49Game
import eu.mgmeiner.lottozahlenapi.game.LottoGameType
import eu.mgmeiner.lottozahlenapi.game.LottoSpiel77Game
import eu.mgmeiner.lottozahlenapi.game.LottoSuper6Game
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.time.LocalDate

class WestlottoLottoDrawSourceTest {

    private lateinit var westlottoLottozahlenSource: WestlottoLottoDrawSource

    @BeforeEach
    fun setUp() {
        westlottoLottozahlenSource = WestlottoLottoDrawSource(LottozahlenAPIConfigProps())
    }

    @Test
    fun `test that parseXml() parses the westlotto valid-result xml correctly`() {
        val xml = javaClass.getResourceAsStream("/westlotto/valid-result.xml").readAllBytes().toString(Charset.forName("UTF-8"))

        val result = westlottoLottozahlenSource.parseXml(xml)

        result.date.should.be.equal(LocalDate.of(2018, 6, 9))

        result.games.should.have.size(3)
        result.games.should.contain(Pair(LottoGameType.LOTTOSUPER6, LottoSuper6Game(826418)))
        result.games.should.contain(Pair(LottoGameType.LOTTOSPIEL77, LottoSpiel77Game(3214733)))
        result.games.should.contain(Pair(LottoGameType.LOTTO6AUS49, Lotto6Aus49Game(listOf(13, 16, 34, 35, 39, 42), 9, null)))
    }

    @Test
    fun `test that createLottozahlenSourceModelFromRawValues() returns LottoDrawModel correct`() {
        val result = westlottoLottozahlenSource.createLottozahlenSourceModelFromRawValues(
                "vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6",
                "Spiel77: 1234567",
                "Super 6: 123456")

        result.date.should.be.equal(LocalDate.of(2018, 6, 12))

        result.games.should.have.size(3)
        result.games.should.contain(Pair(LottoGameType.LOTTOSUPER6, LottoSuper6Game(123456)))
        result.games.should.contain(Pair(LottoGameType.LOTTOSPIEL77, LottoSpiel77Game(1234567)))
        result.games.should.contain(Pair(LottoGameType.LOTTO6AUS49, Lotto6Aus49Game(listOf(8, 9, 10, 11, 12, 13), 6, null)))
    }

    @Test
    fun `test that getSuper6NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSuper6NumbersFromRawString("Super 6: 123456").should.be.equal(123456)
    }

    @Test
    fun `test that getSuper6NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottoDrawSourceException::class.java) {
            westlottoLottozahlenSource.getSuper6NumbersFromRawString("Super 6:")
        }
    }

    @Test
    fun `test that getSpiel77NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSpiel77NumbersFromRawString("Spiel77: 1234567").should.be.equal(1234567)
    }

    @Test
    fun `test that getSpiel77NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottoDrawSourceException::class.java) {
            westlottoLottozahlenSource.getSpiel77NumbersFromRawString("Spiel77:")
        }
    }

    @Test
    fun `test that getDateFromRawString() parses raw string correct and returns date`() {
        westlottoLottozahlenSource.getDateFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(LocalDate.of(2018, 6, 12))
    }

    @Test
    fun `test that getDateFromRawString() fails if could not parse`() {
        assertThrows(LottoDrawSourceException::class.java) {
            westlottoLottozahlenSource.getSpiel77NumbersFromRawString("vom 12.06.2318: 8, 9, 10, 11, 12, 13 S: 6")
        }
    }

    @Test
    fun `test that getSuperzahlFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSuperzahlFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(6)
    }

    @Test
    fun `test that getSuperzahlFromRawString() fails if could not parse`() {
        assertThrows(LottoDrawSourceException::class.java) {
            westlottoLottozahlenSource.getSuperzahlFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: hehe-wrong")
        }
    }

    @Test
    fun `test that get6Aus49NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.get6Aus49NumbersFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(listOf(8, 9, 10, 11, 12, 13))
    }

    @Test
    fun `test that get6Aus49NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottoDrawSourceException::class.java) {
            westlottoLottozahlenSource.get6Aus49NumbersFromRawString("vom 12.06.18: {no-numbers-today} S: 6")
        }
    }
}