package com.example.userappify.voucher

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.userappify.R
import com.example.userappify.model.Voucher


class VoucherAdapter(private val ctx: Context, val vouchers: Array<Voucher>) :
    ArrayAdapter<Voucher>(ctx, R.layout.voucher_list_item, vouchers) {
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: (ctx as Activity).layoutInflater.inflate(
            R.layout.voucher_list_item,
            parent,
            false
        )
        with(vouchers[pos]) {
            row.findViewById<TextView>(R.id.voucherDiscount).text = "$discount % discount"
            row.findViewById<TextView>(R.id.voucherIsUsed).text =
                if (isUsed) "Used" else "Available"

        }
        return row
    }
}