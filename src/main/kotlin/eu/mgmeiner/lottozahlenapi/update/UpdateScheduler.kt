package eu.mgmeiner.lottozahlenapi.update

import eu.mgmeiner.lottozahlenapi.draw.LottoDrawService
import eu.mgmeiner.lottozahlenapi.source.LottoDrawSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(name = ["lottozahlen-api.autoUpdate.enabled"], havingValue = "true")
class UpdateScheduler(
        private val lottoDrawSource: LottoDrawSource,
        private val lottoDrawService: LottoDrawService) {

    @Scheduled(cron = "\${draw-api.autoUpdate.cronSchedule}")
    fun updateLottozahlen() {
        lottoDrawSource.getCurrentLottoDraw().flatMap {
            lottoDrawService.save(it)
        }.subscribe({
            log.debug("successfully retrieved new lotto draw")
        }, {
            log.error("error during getting and saving new lotto draw", it)
        })
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UpdateScheduler::class.java)
    }
}