package com.example.userappify.model

import java.util.*

//TODO fix the datamodel when the server datamodel is fixed

data class Checkout (
    val products: List<Product>,
    val userId: String,
    val voucherId: String? = null,
    val discountNow: Boolean = false,
    val signature: String
)

data class CheckoutToSign (
    val discountNow: Boolean = false,
    val products: List<Product>,
    val userId: String,
    val voucherId: String? = null
)