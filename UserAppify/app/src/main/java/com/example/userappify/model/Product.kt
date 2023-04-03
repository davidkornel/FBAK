package com.example.userappify.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.String

data class Product (
    val id : String,
    val price: Int
)

class ProductHashViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<String>>()

    init {
        _products.value = mutableListOf<String>()
    }
    val products: LiveData<MutableList<String>>
            get() = _products


    fun getProducts(): MutableLiveData<MutableList<String>> {
        return _products
    }

    fun addHashedProduct(product: String){

        _products.value?.add(product)
        println(_products.value)
    }

//    fun addDecodedProduct(product: Product){
//
//        _products.value?.add(product)
//        println(_products.value)
//    }

    fun removeProduct(product: String){
        _products.value?.remove(product)
    }

    fun decodeHashedQrContentToProduct(hash: String){
//        Decode the hash here
//                then call addProduct method to store in the list
//
//        addDecodedProduct(Product(id = "1", price = 1))

    }
}