package com.example.userappify.model

import java.util.*

class Voucher {
    var id: UUID
    var isUsed: Boolean = false
    var discount: Double = 15.0 // 15 %

    constructor(id: UUID, isUsed: Boolean, discount: Double) {
        this.id = id
        this.isUsed = isUsed
        this.discount = discount
    }
}