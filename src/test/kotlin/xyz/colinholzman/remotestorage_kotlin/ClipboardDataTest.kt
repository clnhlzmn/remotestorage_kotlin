package xyz.colinholzman.remotestorage_kotlin

import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Test
import java.util.*

internal class ClipboardDataTest {
    @Test
    fun clipboardDataTest() {
        val data = ClipboardData(Date(), "test")
        val toJson = Gson.gson.toJson(data)
        val backToData = Gson.gson.fromJson<ClipboardData>(toJson, object: TypeToken<ClipboardData>(){}.type)
        assert(data == backToData)
    }
}