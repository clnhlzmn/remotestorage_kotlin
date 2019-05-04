package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*
import java.text.SimpleDateFormat
import java.util.TimeZone



data class ClipboardData(val created: Date, val content: String) {
    constructor() : this(Date(), "")

    class Adapter : TypeAdapter<ClipboardData>() {
        init {
            df.timeZone = tz
        }
        companion object {
            val tz: TimeZone = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        }
        override fun write(out: JsonWriter?, value: ClipboardData?) {
            out!!.beginObject()
            out.name("created")
            val createdISOString = df.format(value!!.created)
            out.value(createdISOString)
            out.name("content")
            out.value(value.content)
            out.endObject()
        }
        override fun read(`in`: JsonReader?): ClipboardData {
            var created: Date? = null
            var content: String? = null
            `in`!!.beginObject()
            while (`in`.hasNext()) {
                val tok = `in`.nextName()
                if (tok!!.contentEquals("created")) {
                    created = df.parse(`in`.nextString())
                }
                if (tok.contentEquals("content")) {
                    content = `in`.nextString()
                }
            }
            `in`.endObject()
            return ClipboardData(created!!, content!!)
        }
    }
}