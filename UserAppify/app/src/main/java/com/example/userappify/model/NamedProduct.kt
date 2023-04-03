package com.example.userappify.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.String

data class NamedProduct(
    val id: UUID,
    val price: Double,
    val name: String
)

class ProductHashViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<NamedProduct>>()

    init {
        _products.value = mutableListOf<NamedProduct>()
    }
    val products: LiveData<MutableList<NamedProduct>>
            get() = _products


    fun getProducts(): MutableLiveData<MutableList<NamedProduct>> {
        return _products
    }

    fun addHashedProduct(product: NamedProduct){

        _products.value?.add(product)
        println(_products.value)
    }

    fun removeProduct(product: NamedProduct){
        _products.value?.remove(product)
    }

    fun removeAllProd() {
       if (_products.value != null) {
           for (prod in _products.value!!) {
               removeProduct(prod)
           }
       }
    }
}