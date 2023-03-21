package com.example.userappify.model

import java.util.*

class NamedProduct : Product {
    var name: String

    constructor(id: UUID, price: Double, name: String) : super(id, price) {
        this.name = name
    }
}