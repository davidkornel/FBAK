package com.example.userappify.model

import java.util.*

data class Transaction (
    val discountNow: Boolean,
    val products: List<Product>,
    val voucherId: UUID?,
    val userId: UUID
) : java.io.Serializable