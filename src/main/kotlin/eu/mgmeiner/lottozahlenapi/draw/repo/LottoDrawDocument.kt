package eu.mgmeiner.lottozahlenapi.draw.repo

import eu.mgmeiner.lottozahlenapi.game.LottoGame
import eu.mgmeiner.lottozahlenapi.game.LottoGameType
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * MongoDB Document for a Lotto draw
 *
 * Dates are stored in old fashioned @see java.util.Date because spring's LocalDateTime Converter makes use of the current timezone and not UTC.
 * <a href="https://jira.spring.io/browse/DATAMONGO-1026">Spring Issue</a>
 */
@Document(collection = "lottodraws")
data class LottoDrawDocument(
        @Id
        val id: ObjectId? = null,
        @Indexed
        val date: Date,
        val saveDateTime: Date,
        val games: Map<LottoGameType, LottoGame>
)

