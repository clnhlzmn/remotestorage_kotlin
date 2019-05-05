package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.reflect.TypeToken
import xyz.colinholzman.remotestorage_kotlin.Gson.Companion.gson

class JSONResourceDescriptor {
    val expires: String? = null
    val subject: String = "subject"
    val aliases: Array<String>? = null
    val properties: Map<String, Any?>? = null
    val links: Array<Link>? = null
    class Link {
        val rel: String = "rel"
        val type: String? = null
        val href: String? = null
        val titles: Map<String, String>? = null
        val properties: Map<String, String?>? = null
    }
    companion object {
        fun fromJson(json: String): JSONResourceDescriptor =
            gson.fromJson<JSONResourceDescriptor>(
                json, object: TypeToken<JSONResourceDescriptor>(){}.type
            )
    }
}

