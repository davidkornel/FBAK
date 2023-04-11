package com.example.userappify.model

import java.util.*

class Voucher(
    var id: UUID, var isUsed: Boolean,// 15 %
    var discount: Double
) {
    var isSelected: Boolean = false

}