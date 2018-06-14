package eu.mgmeiner.lottozahlenapi.lottozahlen.repo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface LottozahlenRepository : ReactiveMongoRepository<LottozahlenDocument, String> {
    fun findByDate(date: Date): Mono<LottozahlenDocument>

    fun findTopByOrderByDateDesc() : Mono<LottozahlenDocument>
}