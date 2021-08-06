package com.orderservice

import com.orderservice.model.ShoppingCartSummary
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class ApplicationTest {

    @Test
    fun testNoItemsInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=0&orangeCount=0").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$0.00", scSummary.TotalCost)
            }
        }
    }
    @Test
    fun test1AppleInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=1&orangeCount=0").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$0.60", scSummary.TotalCost)
            }
        }
    }

    @Test
    fun test1OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=0&orangeCount=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$0.25", scSummary.TotalCost)
            }
        }
    }

    @Test
    fun test1Apple1OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=1&orangeCount=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$0.85", scSummary.TotalCost)
            }
        }
    }
}