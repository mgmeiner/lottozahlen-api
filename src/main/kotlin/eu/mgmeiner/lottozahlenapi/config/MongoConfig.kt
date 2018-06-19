package eu.mgmeiner.lottozahlenapi.config

import eu.mgmeiner.lottozahlenapi.game.LottoGameTypeReadingConverter
import eu.mgmeiner.lottozahlenapi.game.LottoGameTypeWritingConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfig {

    @Bean
    fun mongoCustomConversions() = MongoCustomConversions(listOf(LottoGameTypeReadingConverter(), LottoGameTypeWritingConverter()))
}