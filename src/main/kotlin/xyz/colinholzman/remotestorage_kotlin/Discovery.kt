package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class Discovery {

    companion object {

        fun lookup(userAddress: String, onFailure: (String) -> Unit, onSuccess: (JSONResourceDescriptor) -> Unit) {
            val uaParts = userAddress.split('@')
            //TODO: ensure uaParts.length == 2
            val webfingerQueryUrl =
                URL(
                    "https://${uaParts[1]}/.well-known/webfinger?resource=${URLEncoder.encode("acct:$userAddress", "UTF-8")}")
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
                            val body = response.body()
                            if (body != null) {
                                val result = JSONResourceDescriptor.fromJson(body.string())
                                onSuccess(result)
                            } else {
                                onFailure("no body")
                            }
                        } else {
                            onFailure(response.code().toString())
                        }
                    }
                }
            )
        }

    }

}