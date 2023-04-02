package com.example.userappify.basket

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.userappify.R
import com.google.gson.Gson
import java.util.UUID

data class NamedProduct(
        val id: UUID,
        val price: Double,
        val name: String
){}

data class Basket (
        var products: ArrayList<NamedProduct>
        ){}

data class Product (
        val id : UUID,
        val price: Double
)
class Checkout (
        val products: List<Product>,
        val userId: UUID,
        val voucherId: UUID? = null,
        val discountNow: Boolean = false,
        val signature: String
)

// list for testing

val named_products = arrayListOf<NamedProduct>(
        NamedProduct(UUID.randomUUID(),3.22,"Potatoes"),
        NamedProduct(UUID.randomUUID(),4.33,"Chicken"),
        NamedProduct(UUID.randomUUID(),0.55,"Water")
)
 val basket = Basket(named_products)

fun encode(checkout: Checkout): String {
        return Gson().toJson(checkout).toString()
}

fun getCheckoutFromNamedProducts(
        named_products: ArrayList<NamedProduct>,
        voucherId: UUID,
        userId: UUID,
        discountNow: Boolean = false,
        signature: String
): Checkout {
        var products = arrayListOf<Product>()
        for (prod in named_products) {
                products.add(Product(prod.id,prod.price))
        }
        return Checkout(products,userId,voucherId,discountNow,signature)
}
