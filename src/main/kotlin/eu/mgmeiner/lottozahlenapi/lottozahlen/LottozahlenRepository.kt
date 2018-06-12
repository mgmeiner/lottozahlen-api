package eu.mgmeiner.lottozahlenapi.lottozahlen

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class LottozahlenRepository(private val reactiveMongoTemplate: ReactiveMongoTemplate) {
    fun findById(id: LocalDate) = reactiveMongoTemplate.findOne(Query.query(Criteria.where("_id").`is`(id)), LottozahlenDocument::class.java)

    fun save(lottozahlenDocument: LottozahlenDocument) = reactiveMongoTemplate.save(lottozahlenDocument)

    fun findLatest() = reactiveMongoTemplate.findOne(Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(1), LottozahlenDocument::class.java)
}