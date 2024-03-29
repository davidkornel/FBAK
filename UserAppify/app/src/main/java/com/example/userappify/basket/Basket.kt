package com.example.userappify.basket


import android.widget.*
import com.google.gson.Gson
import java.util.UUID
import com.example.userappify.model.*

// list for testing

fun encode(checkout: Checkout): String {
        return Gson().toJson(checkout).toString()
}

fun getCheckoutFromNamedProducts(
        named_products: ArrayList<NamedProduct>,
        voucherId: UUID?,
        userId: UUID,
        discountNow: Boolean = false,
        signature: String
): Checkout {
        val products = arrayListOf<Product>()
        for (prod in named_products) {
                products.add(Product(prod.id.toString(),prod.price))
        }
        return Checkout(products,userId.toString(),voucherId.toString(),discountNow,signature)
}

fun getCheckoutToSign(
        discountNow: Boolean = false,
        named_products: ArrayList<NamedProduct>,
        userId: UUID,
        voucherId: UUID?
): CheckoutToSign {
        val products = arrayListOf<Product>()
        for (prod in named_products) {
                products.add(Product(prod.id.toString(),prod.price))
        }
        return CheckoutToSign(discountNow,products, userId.toString(),voucherId.toString())
//        return CheckoutToSign(products,userId.toString(),voucherId.toString(),discountNow)
}
