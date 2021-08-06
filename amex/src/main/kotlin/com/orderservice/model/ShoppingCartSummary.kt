package com.orderservice.model

import kotlinx.serialization.Serializable


@Serializable
data class ShoppingCartSummary(val costOfApples: String, val costOfOrange: String, val TotalCost: String)
