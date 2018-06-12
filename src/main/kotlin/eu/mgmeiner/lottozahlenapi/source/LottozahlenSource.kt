package eu.mgmeiner.lottozahlenapi.source

import reactor.core.publisher.Mono

interface LottozahlenSource {
    fun getCurrentLottozahlen(): Mono<LottozahlenSourceModel>
}