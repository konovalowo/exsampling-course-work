package com.konovalovea.expsampling.api

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


class DateDeserializer : JsonDeserializer<Date?> {

    override fun deserialize(
        element: JsonElement,
        arg1: Type?,
        arg2: JsonDeserializationContext?
    ): Date? {
        val date = element.asString
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        format.setTimeZone(TimeZone.getTimeZone("GMT"))
        return try {
            format.parse(date)
        } catch (e: Exception) {
            Log.w("DateDeserializer", e)
            null
        }
    }
}