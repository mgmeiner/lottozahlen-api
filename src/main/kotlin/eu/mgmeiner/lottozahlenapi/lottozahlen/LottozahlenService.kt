package eu.mgmeiner.lottozahlenapi.lottozahlen

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class LottozahlenService(private val lottozahlenRepository: LottozahlenRepository) {

    fun save(lottozahlenDocument: LottozahlenDocument): Mono<LottozahlenDocument> = Mono.just(lottozahlenDocument)
            .flatMap {
                lottozahlenRepository.findById(it.date).switchIfEmpty(Mono.defer {
                    lottozahlenRepository.save(it.copy(saveDateTime = LocalDateTime.now()))
                })
            }

    fun getLatest() = lottozahlenRepository.findLatest()
}