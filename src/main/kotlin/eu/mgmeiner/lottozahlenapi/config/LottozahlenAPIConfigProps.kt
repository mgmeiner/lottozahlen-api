package eu.mgmeiner.lottozahlenapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("lottozahlen-api")
class LottozahlenAPIConfigProps {

    lateinit var westlottoRSSFeedUrl: String
}