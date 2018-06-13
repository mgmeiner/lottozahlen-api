package eu.mgmeiner.lottozahlenapi.source

import eu.mgmeiner.lottozahlenapi.lottozahlen.LottozahlenModel
import reactor.core.publisher.Mono

interface LottozahlenSource {
    fun getCurrentLottozahlen(): Mono<LottozahlenModel>
}