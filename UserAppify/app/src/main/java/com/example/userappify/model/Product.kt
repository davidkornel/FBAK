package com.example.userappify.model

//TODO fix the datamodel when server datamodel is fixed
open class Product(
    var id: String,
    var price: Int //TODO this should be double
) : java.io.Serializable {

}
