package eu.mgmeiner.lottozahlenapi.draw.repo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface LottoDrawRepository : ReactiveMongoRepository<LottoDrawDocument, String> {
    fun findByDate(date: Date): Mono<LottoDrawDocument>

    fun findTopByOrderByDateDesc(): Mono<LottoDrawDocument>
}