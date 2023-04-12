package com.example.userappify.model

import java.util.*
data class Checkout (
    val products: List<Product>,
    val userId: String,
    val voucherId: String? = null,
    val discountNow: Boolean = false,
    val signature: String
)