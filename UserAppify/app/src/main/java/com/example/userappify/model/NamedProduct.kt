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

