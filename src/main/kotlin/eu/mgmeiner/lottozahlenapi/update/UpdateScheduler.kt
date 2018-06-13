package eu.mgmeiner.lottozahlenapi.update

import eu.mgmeiner.lottozahlenapi.lottozahlen.LottozahlenService
import eu.mgmeiner.lottozahlenapi.source.LottozahlenSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(name = ["lottozahlen-api.autoUpdate.enabled"], havingValue = "true")
class UpdateScheduler(
        private val lottozahlenSource: LottozahlenSource,
        private val lottozahlenService: LottozahlenService) {

    @Scheduled(cron = "\${lottozahlen-api.autoUpdate.cronSchedule}")
    fun updateLottozahlen() {
        lottozahlenSource.getCurrentLottozahlen().flatMap {
            lottozahlenService.save(it)
        }.subscribe({
            log.debug("successfully retrieved new 'Lottozahlen'")
        }, {
            log.error("error during getting and saving new 'Lottozahlen'", it)
        })
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UpdateScheduler::class.java)
    }
}