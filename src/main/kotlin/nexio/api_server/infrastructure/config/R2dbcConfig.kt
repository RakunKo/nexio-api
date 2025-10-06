package nexio.api_server.infrastructure.config

import nexio.api_server.domain.BaseEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.CustomConversions
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.MySqlDialect
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import reactor.core.publisher.Mono
import java.nio.ByteBuffer
import java.util.*

@Configuration
@EnableR2dbcAuditing
class R2dbcConfig {
    @Bean
    fun r2dbcConversion(): R2dbcCustomConversions {
        val converters = listOf(UUIDToBytesConverter(), BytesToUUIDConverter(), LongToUUIDConverter())
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters)
    }

    @WritingConverter
    private class UUIDToBytesConverter: Converter<UUID, ByteArray> {
        override fun convert(source: UUID): ByteArray? = ByteBuffer.allocate(16).apply {
            putLong(source.mostSignificantBits)
            putLong(source.leastSignificantBits)
        }.array()
    }

    @ReadingConverter
    private class BytesToUUIDConverter: Converter<ByteArray, UUID> {
        override fun convert(source: ByteArray): UUID? = ByteBuffer.wrap(source)
                .let { UUID(it.long, it.long) }
    }

    @ReadingConverter
    private class LongToUUIDConverter: Converter<Long, UUID> {
        //Ignore r2dbc long id check
        override fun convert(source: Long): UUID? = null
    }
}
