package com.example.userappify.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.userappify.R
import com.example.userappify.model.Transaction

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TransactionDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_detail_activity)
        @Suppress("DEPRECATION") val transaction =
            intent.getSerializableExtra("transaction") as Transaction
        val listView = findViewById<ListView>(R.id.transactionDetailProductsList)
        listView.adapter =
            ProductAdapter(this, transaction.products)
        val totalTextView = findViewById<TextView>(R.id.transactionDetailTotal)
        totalTextView.text = "Total: ${transaction.totalPaid} eur"
    }
}