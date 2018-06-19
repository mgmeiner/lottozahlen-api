package eu.mgmeiner.lottozahlenapi.draw

import eu.mgmeiner.lottozahlenapi.game.GameBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate


@Configuration
class LottozahlenImportRoutes {

    @Bean
    fun lottozahlenImportRoutesProvider(lottoDrawService: LottoDrawService) = router {
        GET("/import") {
            ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(getLotto().concatWith(getLosa()).concatWith(getLomi1()).concatWith(getLomi2()).flatMap {
                lottoDrawService.save(it)
            }, LottoDrawModel::class.java)
        }
    }

    fun getLotto() = Flux.fromStream(Files.lines(Paths.get("/home/michael/Documents/lottolegacy/lotto.txt"))).skip(1).map {
        it.split("\t")
    }.map {
        val day = it[0].toInt()
        val month = it[1].toInt()
        val year = it[2].toInt()

        val date = LocalDate.of(year, month, day)

        val zahlen = listOf(it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt(), it[8].toInt())

        val superZahl = it[10].toIntOrNull()

        LottoDrawModel(date, GameBuilder.create().with6Aus49(zahlen, superZahl).build())
    }

    fun getLosa() = Flux.fromStream(Files.lines(Paths.get("/home/michael/Documents/lottolegacy/losa.txt"))).skip(1).map {
        it.split("\t")
    }.map {
        val day = it[0].toInt()
        val month = it[1].toInt()
        val year = it[2].toInt()

        val date = LocalDate.of(year, month, day)

        val zahlen = listOf(it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt(), it[8].toInt())

        val superZahl = it[10].toIntOrNull()

        LottoDrawModel(date, GameBuilder.create().with6Aus49(zahlen, superZahl).build())
    }

    fun getLomi2() = Flux.fromStream(Files.lines(Paths.get("/home/michael/Documents/lottolegacy/lomi2.txt"))).skip(1).map {
        it.split("\t")
    }.map {
        val day = it[0].toInt()
        val month = it[1].toInt()
        val year = it[2].toInt()

        val date = LocalDate.of(year, month, day)

        val zahlen1 = listOf(it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt(), it[8].toInt())
        val zusatzzahl1 = it[9].toIntOrNull()
        val superzahl1 = it[17].toIntOrNull()

        val zahlen2 = listOf(it[10].toInt(), it[11].toInt(), it[12].toInt(), it[13].toInt(), it[14].toInt(), it[15].toInt())
        val zusatzzahl2 = it[16].toIntOrNull()
        val superzahl2 = it[18].toIntOrNull()

        LottoDrawModel(date, GameBuilder.create().with6Aus49(zahlen1, superzahl1, zusatzzahl1).with6Aus49Ziehung2(zahlen2, superzahl2, zusatzzahl2).build())
    }

    fun getLomi1() = Flux.fromStream(Files.lines(Paths.get("/home/michael/Documents/lottolegacy/lomi1.txt"))).skip(1).map {
        it.split("\t")
    }.map {
        val day = it[0].toInt()
        val month = it[1].toInt()
        val year = it[2].toInt()

        val date = LocalDate.of(year, month, day)

        val zahlen = listOf(it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt(), it[8].toInt(), it[9].toInt())
        val zusatzzahl = it[10].toInt()

        LottoDrawModel(date, GameBuilder.create().with7Aus38(zahlen, zusatzzahl).build())
    }
}