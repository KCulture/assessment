package com.orderservice


import com.orderservice.model.ShoppingCartSummary
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.serialization.*
import java.math.BigDecimal

const val appleCost = 60
const val orangeCost = 25

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module(){
    install(Locations) {}
    install(ContentNegotiation) {json()}


    @Location("/order")
    data class AppleOrange(val appleCount:Int,val orangeCount: Int)

    routing {
        get<AppleOrange>{ route ->
            val costOfApple = route.appleCount*appleCost;
            val costOfOrange = route.orangeCount*orangeCost
            val totalCost = costOfApple+costOfOrange
            call.respond(HttpStatusCode.OK,
                ShoppingCartSummary("$${costOfApple/100}.${costOfApple%100}",
                    "$${costOfOrange/100}.${costOfOrange%100}","$${(totalCost/100)}.${(totalCost%100)}"
                                .let{ if(it.length <= 4) it.padEnd(5,'0') else it } )
            )
        }
    }
}