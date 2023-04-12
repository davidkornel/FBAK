package com.example.userappify.model

import java.util.*


open class Card(
    var id: UUID,
    var number: String,
    var cvc: String,
    var expirationDate: Date
) : java.io.Serializable
