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

const val appleCost = 60
const val orangeCost = 25
@Location("/order")
data class AppleOrange(val appleCount:Int,val orangeCount: Int)
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module(){
    install(Locations) {}
    install(ContentNegotiation) {json()}




    routing {
        get<AppleOrange>{
            call.respond(HttpStatusCode.OK,discountSummary(it)
            )
        }
    }
}
//Normally we would create a service package with a service didn't to keep in one file also not using Dependency Injection
fun discountSummary(summary:AppleOrange):ShoppingCartSummary{
    fun fruitDiscountPrice(fruitCount:Int, unitForFreeFruit:Int,unitPrice:Int):Int{
        val freeFruit = (fruitCount/unitForFreeFruit)
        return (fruitCount - freeFruit).toInt() * unitPrice
    }

    fun discountApplePrice(appleCount: Int):Int = fruitDiscountPrice(appleCount,2, appleCost)
    fun discountOrangePrice(appleCount: Int):Int = fruitDiscountPrice(appleCount,3, orangeCost)

    fun displayValue(unformattedPrice:Int):String{
        return "$${unformattedPrice / 100}.${unformattedPrice % 100}".let{ if(it.length <= 4) it.padEnd(5,'0') else it }
    }
    val costOfApple = discountApplePrice(summary.appleCount);
    val costOfOrange = discountOrangePrice(summary.orangeCount)
    val totalCost = costOfApple + costOfOrange
    return ShoppingCartSummary(displayValue(costOfApple),displayValue(costOfOrange),displayValue(totalCost))
}