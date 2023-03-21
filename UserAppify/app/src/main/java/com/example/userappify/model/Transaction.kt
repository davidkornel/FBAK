package com.example.userappify.model

import java.util.*

class Transaction {
    var id: UUID
    var products: List<Product>
    var voucherId: UUID
    var userId: UUID
    var signature: String

    constructor(
        id: UUID,
        products: List<Product>,
        voucherId: UUID,
        userId: UUID,
        signature: String
    ) {
        this.id = id
        this.products = products
        this.voucherId = voucherId
        this.userId = userId
        this.signature = signature
    }
}