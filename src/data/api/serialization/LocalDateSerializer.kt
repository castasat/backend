package backend.data.api.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExperimentalSerializationApi
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
    private val formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN)

    override val descriptor: SerialDescriptor =
        object : SerialDescriptor {
            override val elementsCount: Int = 1
            override val kind: SerialKind = PrimitiveKind.STRING
            override val serialName = "LocalDate"
            override fun getElementAnnotations(index: Int): List<Annotation> = emptyList()
            override fun getElementDescriptor(index: Int) = this
            override fun getElementIndex(name: String): Int = CompositeDecoder.UNKNOWN_NAME
            override fun getElementName(index: Int): String = throw IllegalStateException()
            override fun isElementOptional(index: Int): Boolean = false
        }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}