package eu.mgmeiner.lottozahlenapi.lottozahlen.repo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * MongoDB Document for 'Lottozahlen'
 *
 * Dates are stored in old fashioned @see java.util.Date because spring's LocalDateTime Converter makes use of the current timezone and not UTC.
 * <a href="https://jira.spring.io/browse/DATAMONGO-1026">Spring Issue</a>
 */
@Document(collection = "lottozahlen")
data class LottozahlenDocument (
        @Id
        val id: ObjectId? = null,
        @Indexed
        val date: Date,
        val saveDateTime: Date,
        val spiel77: Int?,
        val super6: Int?,
        val lotto6Aus49: Lotto6Aus49Document
)

data class Lotto6Aus49Document(
        val superzahl: Int?,
        val zahlen: List<Int>
)