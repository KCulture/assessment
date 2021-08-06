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

    @Test
    fun test2Apple1OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=2&orangeCount=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$0.85", scSummary.TotalCost)
            }
        }
    }

    @Test
    fun test2Apple2OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=2&orangeCount=2").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$1.10", scSummary.TotalCost)
            }
        }
    }
    @Test
    fun test2Apple3OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=2&orangeCount=3").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$1.10", scSummary.TotalCost)
            }
        }
    }
    @Test
    fun test4Apple6OrangeInOrder() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/order?appleCount=4&orangeCount=6").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
                assertEquals("$2.20", scSummary.TotalCost)
            }
        }
    }
}