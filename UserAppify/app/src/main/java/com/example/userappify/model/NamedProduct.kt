package com.example.userappify.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class NamedProduct(
    var id: UUID,
    var price: Double,
    var name: String
) : java.io.Serializable {

}

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

    fun addHashedProduct(product: NamedProduct) {

        _products.value?.add(product)
        println(_products.value)
    }

    fun removeProduct(product: NamedProduct) {
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