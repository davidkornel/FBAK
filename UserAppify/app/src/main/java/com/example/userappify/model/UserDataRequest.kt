package com.example.userappify.model

data class UserDataRequest(
    val signature: String,
    val userId: String
)

data class UserDataRequestToSign(
    val userId: String
)
