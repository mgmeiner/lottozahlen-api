package eu.mgmeiner.lottozahlenapi.source

import eu.mgmeiner.lottozahlenapi.lottozahlen.LottozahlenDocument
import reactor.core.publisher.Mono

interface LottozahlenSource {
    fun getCurrentLottozahlen(): Mono<LottozahlenDocument>
}