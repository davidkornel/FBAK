package com.example.userappify.model

import java.util.*

open class RegistrationUser(
    var username: String,
    var password: String?,
    var email: String,
    var name: String,
    var surname: String,
    var publicKey: String,
    var transactions: List<Transaction>,
    var vouchers: List<Voucher>,
    var card: Card,
) : java.io.Serializable

class User(
    var id: UUID,
    username: String,
    password: String?,
    email: String,
    name: String,
    surname: String,
    publicKey: String,
    transactions: List<Transaction>,
    vouchers: List<Voucher>,
    card: Card

) : RegistrationUser(
    username, password, email, name, surname, publicKey, transactions, vouchers, card
)