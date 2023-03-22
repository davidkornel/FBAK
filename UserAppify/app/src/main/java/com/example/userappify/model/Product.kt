package com.example.userappify.model

import java.util.*

open class Product : java.io.Serializable {
    var id: UUID
    var price: Double

    constructor(id: UUID, price: Double) {
        this.id = id
        this.price = price
    }
}
