package eu.mgmeiner.lottozahlenapi

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
class LottozahlenApiApplication

fun main(args: Array<String>) {
    runApplication<LottozahlenApiApplication>(*args)
}
