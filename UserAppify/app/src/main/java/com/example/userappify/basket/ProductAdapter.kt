package com.example.userappify.basket

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.userappify.R
import com.example.userappify.model.*


class ProductAdapter(private val ctx: Context, val products: ArrayList<NamedProduct>, val viewModel: ProductHashViewModel): BaseAdapter() {
    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(i: Int): Any {
        return products[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: (ctx as Activity).layoutInflater.inflate(
            R.layout.list_item,
            parent,
            false
        )
        with(products[pos]) {
            row.findViewById<TextView>(R.id.tv_lst_name).text = name
            row.findViewById<TextView>(R.id.tv_lst_price).text = price.toString()
            row.findViewById<ImageButton>(R.id.btn_rm_prod).setOnClickListener{
                viewModel.removeProduct(products[pos])
                notifyDataSetChanged()
            }
        }

        return row as View
    }
}