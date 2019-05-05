package xyz.colinholzman.remotestorage_kotlin

import org.junit.jupiter.api.Test

internal class DiscoveryTest {
    @Test
    fun test() {
        Discovery.lookup(
            "test42@5apps.com",
            { assert(false) },
            { }
        )
    }
}