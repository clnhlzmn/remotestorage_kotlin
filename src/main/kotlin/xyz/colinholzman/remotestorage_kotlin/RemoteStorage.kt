package xyz.colinholzman.remotestorage_kotlin

import okhttp3.*
import java.io.IOException

class RemoteStorage(
    var href: String,
    var token: String
) {

    private fun createPutCall(path: String, value: String): Call {
        return Http.client.newCall(
            Request.Builder()
                .url("$href$path")
                .method("PUT", RequestBody.create(MediaType.parse("text/plain"), value))
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }

    fun put(path: String, value: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {
        createPutCall(path, value)
            .enqueue(
                object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure(e.toString())
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (response.code() in 200..299)
                            onSuccess(response.code().toString())
                        else
                            onFailure(response.code().toString())
                    }
                }
            )
    }

    fun putSync(path: String, value: String): Boolean {
        return createPutCall(path, value).execute().code() in 200..299
    }

    private fun createGetCall(path: String): Call {
       return Http.client.newCall(
           Request.Builder()
               .url("$href$path")
               .method("GET", null)
               .addHeader("Authorization", "Bearer $token")
               .build()
           )
    }

    fun get(path: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {
        createGetCall(path)
            .enqueue(
                object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onFailure(e.toString())
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (response.code() in 200..299)
                            onSuccess(response.body()!!.string())
                        else
                            onFailure(response.code().toString())
                    }
                }
            )
    }

    fun getSync(path: String): String? {
        return createGetCall(path).execute().body()?.string()
    }

    fun delete(path: String, onFailure: (String) -> Unit, onSuccess: (String) -> Unit) {

    }

    fun deleteSync(path: String): Boolean {
        return true
    }

}