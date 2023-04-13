package com.example.userappify.model

data class UserDataResponse (
    val availableVouchersId: List<String>,
    val lastTransactions: List<Transaction>
)