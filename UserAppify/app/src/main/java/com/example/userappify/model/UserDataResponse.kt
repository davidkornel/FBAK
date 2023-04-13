package com.example.userappify.model

data class UserDataResponse (
    val availableVoucherIds: List<String>,
    val lastTransactions: List<Transaction>
)