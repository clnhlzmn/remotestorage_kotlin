package xyz.colinholzman.remotestorage_kotlin

import java.net.URL

class Authorization {
    companion object {

        private const val clientId = "colinholzman.xyz"
        val redirectUrl = URL("https://rssync.colinholzman.xyz")
        private const val scope = "clipboard:rw"

        fun getHref(jrd: JSONResourceDescriptor): String? {
            return jrd.links!![0].href
        }

        fun getAuthQuery(jrd: JSONResourceDescriptor): URL {
            val authUrlString = jrd.links?.get(0)?.properties?.get("http://tools.ietf.org/html/rfc6749#section-4.2")
            val authQuery = "$authUrlString?client_id=$clientId&response_type=token&redirect_uri=$redirectUrl&scope=$scope"
            return URL(authQuery)
        }

        fun getVersion(jrd: JSONResourceDescriptor): String {
            return jrd.links!![0].properties!!["http://remotestorage.io/spec/version"]!!
        }
    }
}