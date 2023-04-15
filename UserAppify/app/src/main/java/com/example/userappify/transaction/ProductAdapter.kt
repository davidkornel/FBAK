package com.example.userappify.transaction

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.userappify.R
import com.example.userappify.model.NamedProduct
import com.example.userappify.model.Product
import com.example.userappify.model.Transaction
import com.example.userappify.model.Voucher


class ProductAdapter(private val ctx: Context, val products: List<Product>) :
    ArrayAdapter<Product>(ctx, R.layout.product_list_item, products) {
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: (ctx as Activity).layoutInflater.inflate(
            R.layout.product_list_item,
            parent,
            false
        )
        with(products[pos]) {
            row.findViewById<TextView>(R.id.productName).text =
                "Product ${id.substring(0, 4)}..."
            row.findViewById<TextView>(R.id.productPrice).text = "$price eur"
        }
        return row
    }
}