package com.example.userappify.transaction

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.userappify.R
import com.example.userappify.model.Transaction


class TransactionAdapter(private val ctx: Context, val transactions: Array<Transaction>) :
    ArrayAdapter<Transaction>(ctx, R.layout.transaction_list_item, transactions) {
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: (ctx as Activity).layoutInflater.inflate(
            R.layout.transaction_list_item,
            parent,
            false
        )
        with(transactions[pos]) {
            row.findViewById<TextView>(R.id.transactionName).text = "Transaction"
            var total = totalPaid
            row.findViewById<TextView>(R.id.transactionPrice).text = "${total} eur"
        }
        return row
    }
}