package eu.mgmeiner.lottozahlenapi.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun enableEnumToStringInJacksonObjectMapper() =
            Jackson2ObjectMapperBuilderCustomizer { it.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, DeserializationFeature.READ_ENUMS_USING_TO_STRING) }
}