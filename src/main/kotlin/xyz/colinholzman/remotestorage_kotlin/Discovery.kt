package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class Discovery {

    companion object {

        private val gson = Gson()

        fun lookup(userAddress: String, onFailure: (String) -> Unit, onSuccess: (JSONResourceDescriptor) -> Unit) {
            val uaParts = userAddress.partition { c -> c == '@' }
            val webfingerQueryUrl =
                URL(
                    "https://${uaParts.first}/.well-known/webfinger/?resource=${URLEncoder.encode(userAddress, "UTF-8")}")
            val webfingerQueryRequest = Request.Builder()
                .method("GET", null)
                .url(webfingerQueryUrl)
                .build()
            Http.client.newCall(webfingerQueryRequest).enqueue(
                object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure(e.toString())
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (response.code() == 200) {
                            val result =
                                gson.fromJson<JSONResourceDescriptor>(
                                    response.body()?.string(),
                                    object: TypeToken<JSONResourceDescriptor>(){}.type
                                )
                            onSuccess(result)
                        } else {
                            onFailure(response.code().toString())
                        }
                    }
                }
            )
        }

    }

}