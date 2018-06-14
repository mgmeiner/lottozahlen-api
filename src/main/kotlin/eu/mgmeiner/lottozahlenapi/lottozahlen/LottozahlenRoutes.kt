package eu.mgmeiner.lottozahlenapi.lottozahlen

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.router

@Configuration
class LottozahlenRoutes {
    @Bean
    fun lottozahlenRoutesProvider(lottozahlenService: LottozahlenService) = router {
        GET("/") {
            val latest = lottozahlenService.getLatest()

            latest.flatMap {
                ok().contentType(MediaType.APPLICATION_JSON).body(latest, LottozahlenModel::class.java)
            }.switchIfEmpty(status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).syncBody("no 'Lottozahlen' available"))
        }
    }
}