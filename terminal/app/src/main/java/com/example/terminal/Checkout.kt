package com.example.terminal

import com.google.gson.Gson
import java.util.UUID
// TODO fix the datamodel when the server's datamodel is fixed
data class Product (
    val id : String,
    val price: Int
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

var products = arrayListOf<Product>(
    Product(UUID.randomUUID().toString(),3),
    Product(UUID.randomUUID().toString(),4),
    Product(UUID.randomUUID().toString(),4),
    Product(UUID.randomUUID().toString(),4),
    Product(UUID.randomUUID().toString(),4),
    Product(UUID.randomUUID().toString(),4)
)

var checkout = Checkout(products,UUID.randomUUID().toString(), signature = "user_signature")

fun encode(checkout: Checkout): String {
    return Gson().toJson(checkout).toString()
}

