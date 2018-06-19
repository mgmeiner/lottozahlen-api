package eu.mgmeiner.lottozahlenapi.source

import eu.mgmeiner.lottozahlenapi.draw.LottoDrawModel
import reactor.core.publisher.Mono

interface LottoDrawSource {
    fun getCurrentLottoDraw(): Mono<LottoDrawModel>
}