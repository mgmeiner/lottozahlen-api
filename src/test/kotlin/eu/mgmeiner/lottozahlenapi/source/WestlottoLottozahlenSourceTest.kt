package eu.mgmeiner.lottozahlenapi.source

import com.winterbe.expekt.should
import eu.mgmeiner.lottozahlenapi.config.LottozahlenAPIConfigProps
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.time.LocalDate

class WestlottoLottozahlenSourceTest {

    private lateinit var westlottoLottozahlenSource: WestlottoLottozahlenSource

    @BeforeEach
    fun setUp() {
        westlottoLottozahlenSource = WestlottoLottozahlenSource(LottozahlenAPIConfigProps())
    }

    @Test
    fun `test that parseXml() parses the westlotto valid-result xml correctly`() {
        val xml = javaClass.getResourceAsStream("/westlotto/valid-result.xml").readAllBytes().toString(Charset.forName("UTF-8"))

        val result = westlottoLottozahlenSource.parseXml(xml)

        result.date.should.be.equal(LocalDate.of(2018, 6, 9))
        result.super6.should.be.equal(listOf(8, 2, 6, 4, 1, 8))
        result.spiel77.should.be.equal(listOf(3, 2, 1, 4, 7, 3, 3))

        result.lottozahlen6aus49SourceModel.superzahl.should.be.equal(9)
        result.lottozahlen6aus49SourceModel.zahlen.should.be.equal(listOf(13, 16, 34, 35, 39, 42))
    }

    @Test
    fun `test that createLottozahlenSourceModelFromRawValues() returs LottozahlenSourceModel correct`() {
        val result = westlottoLottozahlenSource.createLottozahlenSourceModelFromRawValues(
                "vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6",
                "Spiel77: 1234567",
                "Super 6: 123456")

        result.date.should.be.equal(LocalDate.of(2018, 6, 12))
        result.super6.should.be.equal(listOf(1, 2, 3, 4, 5, 6))
        result.spiel77.should.be.equal(listOf(1, 2, 3, 4, 5, 6, 7))

        result.lottozahlen6aus49SourceModel.superzahl.should.be.equal(6)
        result.lottozahlen6aus49SourceModel.zahlen.should.be.equal(listOf(8, 9, 10, 11, 12, 13))
    }

    @Test
    fun `test that getSuper6NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSuper6NumbersFromRawString("Super 6: 123456").should.be.equal(listOf(1, 2, 3, 4, 5, 6))
    }

    @Test
    fun `test that getSuper6NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottozahlenSourceException::class.java) {
            westlottoLottozahlenSource.getSuper6NumbersFromRawString("Super 6:")
        }
    }

    @Test
    fun `test that getSpiel77NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSpiel77NumbersFromRawString("Spiel77: 1234567").should.be.equal(listOf(1, 2, 3, 4, 5, 6, 7))
    }

    @Test
    fun `test that getSpiel77NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottozahlenSourceException::class.java) {
            westlottoLottozahlenSource.getSpiel77NumbersFromRawString("Spiel77:")
        }
    }

    @Test
    fun `test that getDateFromRawString() parses raw string correct and returns date`() {
        westlottoLottozahlenSource.getDateFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(LocalDate.of(2018, 6, 12))
    }

    @Test
    fun `test that getDateFromRawString() fails if could not parse`() {
        assertThrows(LottozahlenSourceException::class.java) {
            westlottoLottozahlenSource.getSpiel77NumbersFromRawString("vom 12.06.2318: 8, 9, 10, 11, 12, 13 S: 6")
        }
    }

    @Test
    fun `test that getSuperzahlFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.getSuperzahlFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(6)
    }

    @Test
    fun `test that getSuperzahlFromRawString() fails if could not parse`() {
        assertThrows(LottozahlenSourceException::class.java) {
            westlottoLottozahlenSource.getSuperzahlFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: hehe-wrong")
        }
    }

    @Test
    fun `test that get6Aus49NumbersFromRawString() parses raw string correct`() {
        westlottoLottozahlenSource.get6Aus49NumbersFromRawString("vom 12.06.18: 8, 9, 10, 11, 12, 13 S: 6").should.be.equal(listOf(8, 9, 10, 11, 12, 13))
    }

    @Test
    fun `test that get6Aus49NumbersFromRawString() fails if could not parse`() {
        assertThrows(LottozahlenSourceException::class.java) {
            westlottoLottozahlenSource.get6Aus49NumbersFromRawString("vom 12.06.18: {no-numbers-today} S: 6")
        }
    }
}