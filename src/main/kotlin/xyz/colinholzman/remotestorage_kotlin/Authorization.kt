package xyz.colinholzman.remotestorage_kotlin

import java.net.URL
import java.util.HashMap

class Authorization {
    companion object {

        private const val clientId = "colinholzman.xyz"
        val redirectUrl = "https://rssync.colinholzman.xyz"
        private const val scope = "clipboard:rw"

        fun getHref(jrd: JSONResourceDescriptor): String {
            return jrd.links!![0].href!!
        }

        fun getAuthQuery(jrd: JSONResourceDescriptor): String {
            val authUrlString = jrd.links?.get(0)?.properties?.get("http://tools.ietf.org/html/rfc6749#section-4.2")
            val authQuery = "$authUrlString?client_id=$clientId&response_type=token&redirect_uri=$redirectUrl&scope=$scope"
            return authQuery
        }

        fun getVersion(jrd: JSONResourceDescriptor): String {
            return jrd.links!![0].properties!!["http://remotestorage.io/spec/version"]!!
        }

        //get a map of parameters from a url fragment encoded in application/x-www-form-urlencoded
        fun getMap(fragment: String): Map<String, String> {
            val params = fragment.split("&")
            val map = HashMap<String, String>()
            for (param in params) {
                val pair = param.split("=").dropLastWhile { it.isEmpty() }
                map[pair[0]] = pair[1]
            }
            return map
        }
    }
}