package eu.mgmeiner.lottozahlenapi.lottozahlen

import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.Lotto6Aus49Document
import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.LottozahlenDocument
import eu.mgmeiner.lottozahlenapi.lottozahlen.repo.LottozahlenRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class LottozahlenService(private val lottozahlenRepository: LottozahlenRepository) {
    fun LocalDate.toUTCDate(): Date = this.atStartOfDay().toUTCDate()
    fun LocalDateTime.toUTCDate(): Date = Date.from(this.atZone(ZoneId.of("UTC")).toInstant())

    fun save(lottozahlenModel: LottozahlenModel): Mono<LottozahlenModel> {
        return lottozahlenRepository.findByDate(lottozahlenModel.date.toUTCDate()).switchIfEmpty(Mono.defer {
            lottozahlenRepository.save(modelToDocument(lottozahlenModel))
        }).map { documentToModel(it) }
    }

    fun getLatest() = lottozahlenRepository
            .findTopByOrderByDateDesc()
            .map { documentToModel(it) }

    private fun modelToDocument(model: LottozahlenModel) = LottozahlenDocument(
            date = model.date.toUTCDate(),
            super6 = model.super6,
            spiel77 = model.spiel77,
            lotto6Aus49 = Lotto6Aus49Document(
                    superzahl = model.lotto6Aus49.superzahl,
                    zahlen = model.lotto6Aus49.zahlen
            ),
            saveDateTime = LocalDateTime.now().toUTCDate()
    )

    private fun documentToModel(doc: LottozahlenDocument) = LottozahlenModel(
            date = LocalDate.ofInstant(doc.date.toInstant(), ZoneId.of("UTC")),
            super6 = doc.super6,
            spiel77 = doc.spiel77,
            lotto6Aus49 = Lotto6Aus49Model(
                    superzahl = doc.lotto6Aus49.superzahl,
                    zahlen = doc.lotto6Aus49.zahlen
            )
    )
}