package eu.mgmeiner.lottozahlenapi.game

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

@Component
@ReadingConverter
class LottoGameTypeReadingConverter : Converter<String, LottoGameType> {
    override fun convert(source: String) = LottoGameType.parse(source)
}

@Component
@WritingConverter
class LottoGameTypeWritingConverter : Converter<LottoGameType, String> {
    override fun convert(source: LottoGameType) = source.toString()
}