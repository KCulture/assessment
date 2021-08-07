package com.orderservice

import com.orderservice.model.ShoppingCartSummary
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class ApplicationTest {

    @Test
    fun testNoItemsInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":0,\n" +
                            "\t\"orangeCount\":0\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$0.00", scSummary.TotalCost)
    }

    @Test
    fun test1AppleInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":1,\n" +
                            "\t\"orangeCount\":0\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$0.60", scSummary.TotalCost)
    }

    @Test
    fun test1OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":0,\n" +
                            "\t\"orangeCount\":1\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$0.25", scSummary.TotalCost)

    }

    @Test
    fun test1Apple1OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":1,\n" +
                            "\t\"orangeCount\":1\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$0.85", scSummary.TotalCost)
    }

    @Test
    fun test2Apple1OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":2,\n" +
                            "\t\"orangeCount\":1\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$0.85", scSummary.TotalCost)
    }


    @Test
    fun test2Apple2OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":2,\n" +
                            "\t\"orangeCount\":2\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$1.10", scSummary.TotalCost)
    }

    @Test
    fun test2Apple3OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":2,\n" +
                            "\t\"orangeCount\":3\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$1.10", scSummary.TotalCost)
    }

    @Test
    fun test4Apple6OrangeInOrder() {
        val call = withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":4,\n" +
                            "\t\"orangeCount\":6\n" +
                            "}"
                )
            }
        }

        val response = call.response
        assertEquals(HttpStatusCode.OK, response.status())
        val scSummary = Json.decodeFromString<ShoppingCartSummary>(response.content!!)
        assertEquals("$2.20", scSummary.TotalCost)
    }

    @Test
    fun getOrders0() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":0,\n" +
                            "\t\"orangeCount\":0\n" +
                            "}"
                )
            }
        }

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/orders/0").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.isNotEmpty())
            }


        }
    }

    @Test
    fun getAllOrders() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    "{\n" +
                            "\t\"appleCount\":0,\n" +
                            "\t\"orangeCount\":0\n" +
                            "}"
                )
            }
        }

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/orders").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.isNotEmpty())
            }


        }
    }

}