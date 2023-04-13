package com.example.userappify.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ProductHashViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<NamedProduct>>()
    val products: LiveData<MutableList<NamedProduct>>
        get() = _products

    private val _vouchers = MutableLiveData<MutableList<Voucher>>()
    val vouchers: LiveData<MutableList<Voucher>>
        get() = _vouchers

    init {
        _products.value = mutableListOf()
        _vouchers.value = mutableListOf()
    }

    fun getProducts(): MutableLiveData<MutableList<NamedProduct>> {
        return _products
    }

    fun getSelectedVoucher(): Voucher? {
        return _vouchers.value?.firstOrNull() { it.isSelected }
    }

    fun addHashedProduct(product: NamedProduct) {
        _products.value?.add(product)
    }

    fun removeProduct(product: NamedProduct) {
        _products.value?.remove(product)
    }

    fun removeAllProd() {
        _products.value!!.clear()

    }

    fun updateVouchers(newVouchers: List<Voucher>) {
        _vouchers.value!!.clear()
        for (v in newVouchers) {
            _vouchers.value?.add(v)
        }
    }

}