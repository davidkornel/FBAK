package com.example.userappify.model

import java.util.*


open class Card(
    var id: UUID,
    var number: String,
    var csv: String,
    var expirationDate: Date
) : java.io.Serializable
