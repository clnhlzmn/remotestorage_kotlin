package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.GsonBuilder

class Gson {
    companion object {
        val gson = GsonBuilder()
            .registerTypeAdapter(ClipboardData().javaClass, ClipboardData.Adapter())
            .create()
    }
}