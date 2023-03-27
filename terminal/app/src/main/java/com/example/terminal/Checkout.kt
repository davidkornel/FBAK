package com.example.terminal

import java.util.UUID

data class Product (
    val id : String,
    val price: Int
        )
data class Checkout (
    val products: List<Product>,
    val userId: String,
    val voucherId: String? = null,
    val discountNow: Boolean = false,
    val signature: String
    )

var products = listOf<Product>(
    Product(UUID.randomUUID().toString(),3),
    Product(UUID.randomUUID().toString(),4)
)

var checkout = Checkout(products,UUID.randomUUID().toString(), signature = "user_signature")