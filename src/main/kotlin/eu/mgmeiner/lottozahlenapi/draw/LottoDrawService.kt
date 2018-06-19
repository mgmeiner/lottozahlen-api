package eu.mgmeiner.lottozahlenapi.draw

import eu.mgmeiner.lottozahlenapi.draw.repo.LottoDrawDocument
import eu.mgmeiner.lottozahlenapi.draw.repo.LottoDrawRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class LottoDrawService(private val lottoDrawRepository: LottoDrawRepository) {
    fun LocalDate.toUTCDate(): Date = this.atStartOfDay().toUTCDate()
    fun LocalDateTime.toUTCDate(): Date = Date.from(this.atZone(ZoneId.of("UTC")).toInstant())

    fun save(lottoDrawModel: LottoDrawModel): Mono<LottoDrawModel> {
        return lottoDrawRepository.findByDate(lottoDrawModel.date.toUTCDate()).switchIfEmpty(Mono.defer {
            lottoDrawRepository.save(modelToDocument(lottoDrawModel))
        }).map { documentToModel(it) }
    }

    fun getLatest() = lottoDrawRepository
            .findTopByOrderByDateDesc()
            .map { documentToModel(it) }

    private fun modelToDocument(model: LottoDrawModel) = LottoDrawDocument(
            date = model.date.toUTCDate(),
            saveDateTime = LocalDateTime.now().toUTCDate(),
            games = model.games
    )

    private fun documentToModel(doc: LottoDrawDocument) = LottoDrawModel(
            date = LocalDate.ofInstant(doc.date.toInstant(), ZoneId.of("UTC")),
            games = doc.games
    )
}