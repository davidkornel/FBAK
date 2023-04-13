package com.example.userappify.model

import java.util.*

class Voucher(
    var id: UUID, var isUsed: Boolean = false,
    var discount: Double = 15.0
) {
    var isSelected: Boolean = false

}