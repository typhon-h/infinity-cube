package com.typhonh.infinitycube.model.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class EnumSerializer<T : Enum<T>> : JsonDeserializer<T> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T? {
        if (json != null && json.isJsonNull) {
            return null
        }

        return try {
            val enumClass = typeOfT as Class<T>

            return enumClass.enumConstants.first {
                it.name.lowercase() == json!!.asString
            }
        } catch (e : IllegalArgumentException) {
            null
        }
    }
}