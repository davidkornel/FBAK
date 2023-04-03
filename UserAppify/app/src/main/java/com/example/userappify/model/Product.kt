package com.example.userappify.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.String

data class Product (
    val id : String,
    val price: Int
)

class ProductHashViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<String>>()
    val products: LiveData<MutableList<String>>
        get() = _products

    private val _vouchers = MutableLiveData<MutableList<Voucher>>()
    val vouchers: LiveData<MutableList<Voucher>>
        get() = _vouchers



    init {
        _products.value = mutableListOf()
        _vouchers.value = mutableListOf(Voucher(UUID.randomUUID(), false, 15.0),
            Voucher(UUID.randomUUID(), true, 69.0))
    }

    fun getSelectedVoucher(): Voucher? {
        return _vouchers.value?.firstOrNull() { it.isSelected }
    }


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