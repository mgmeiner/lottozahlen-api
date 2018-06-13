package eu.mgmeiner.lottozahlenapi.lottozahlen

import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.Lotto6Aus49Document
import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.LottozahlenDocument
import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.LottozahlenRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.*

@Service
class LottozahlenService(private val lottozahlenRepository: LottozahlenRepository) {

    fun save(lottozahlenModel: LottozahlenModel): Mono<LottozahlenModel> {
        val id = lottozahlenModel.date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

        return lottozahlenRepository.findById(id).switchIfEmpty(Mono.defer {
            lottozahlenRepository.save(modelToDocument(lottozahlenModel))
        }).map { documentToModel(it) }
    }

    fun getLatest() = lottozahlenRepository.findLatest()

    private fun modelToDocument(model: LottozahlenModel) = LottozahlenDocument(
            date = model.date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
            super6 = model.super6,
            spiel77 = model.spiel77,
            lotto6Aus49 = Lotto6Aus49Document(
                    superzahl = model.lotto6Aus49.superzahl,
                    zahlen = model.lotto6Aus49.zahlen
            ),
            saveDateTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )

    private fun documentToModel(doc: LottozahlenDocument) = LottozahlenModel(
            date = LocalDate.ofInstant(Instant.ofEpochMilli(doc.date), ZoneId.of("UTC")),
            super6 = doc.super6,
            spiel77 = doc.spiel77,
            lotto6Aus49 = Lotto6Aus49Model(
                    superzahl = doc.lotto6Aus49.superzahl,
                    zahlen = doc.lotto6Aus49.zahlen
            )
    )
}