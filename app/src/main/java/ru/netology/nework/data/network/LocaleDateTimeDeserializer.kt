package ru.netology.nework.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocaleDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        val text = json.asString.let {
            return@let if (it.contains('.')) {
                it.substringBefore('.')
            } else {
                it.replace("Z", "")
            }
        }
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(Constants.SERVER_ISO_FACT))
    }

}
