package com.example.terminal

import com.google.gson.Gson
import java.util.UUID

data class Product (
    val id : String,
    val price: Double
        )
class Checkout (
    val products: List<Product>,
    val userId: String,
    val voucherId: String? = null,
    val discountNow: Boolean = false,
    val signature: String
    )

data class Response (
    val totalAmountPaid: Double
        )
data class ErrorResponse (
    val errorMsg: String
        )
var products = arrayListOf<Product>(
    Product(UUID.randomUUID().toString(),3.0),
    Product(UUID.randomUUID().toString(),4.0),
    Product(UUID.randomUUID().toString(),4.0),
    Product(UUID.randomUUID().toString(),4.0),
    Product(UUID.randomUUID().toString(),4.0),
    Product(UUID.randomUUID().toString(),4.0)
)

var checkout = Checkout(products,UUID.randomUUID().toString(), signature = "user_signature")

fun encode(checkout: Checkout): String {
    return Gson().toJson(checkout).toString()
}

