package xyz.colinholzman.remotestorage_kotlin

import okhttp3.*
import java.io.IOException

class RemoteStorage(
    var href: String,
    var token: String
) {

    fun put(path: String, value: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {
        Http.client.newCall(
            Request.Builder()
                .url("$href$path")
                .method("PUT", RequestBody.create(MediaType.parse("text/plain"), value))
                .addHeader("Authorization", "Bearer $token")
                .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.code() in 200..299)
                    onSuccess(response.code().toString())
                else
                    onFailure(response.code().toString())
            }
        })
    }

    fun putSync(path: String, value: String): Boolean {
        val res = Http.client.newCall(
            Request.Builder()
                .url("$href$path")
                .method("PUT", RequestBody.create(MediaType.parse("text/plain"), value))
                .addHeader("Authorization", "Bearer $token")
                .build()
        ).execute()
        return res.code() in 200..299
    }

    fun get(path: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {
        Http.client.newCall(
        Request.Builder()
            .url("$href$path")
            .method("GET", null)
            .addHeader("Authorization", "Bearer $token")
            .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.code() in 200..299)
                    onSuccess(response.body()!!.string())
                else
                    onFailure(response.code().toString())
            }
        })
    }

    fun getSync(path: String): String? {
        return Http.client.newCall(
            Request.Builder()
                .url("$href$path")
                .method("GET", null)
                .addHeader("Authorization", "Bearer $token")
                .build()
        ).execute().body()?.string()
    }

    fun delete(path: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {

    }

}