package com.example.userappify.transaction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.userappify.R
import com.example.userappify.api.getUserData
import com.example.userappify.databinding.FragmentVoucherBinding
import com.example.userappify.model.NamedProduct
import com.example.userappify.model.Transaction
import com.example.userappify.model.UserDataResponse
import com.example.userappify.model.Voucher
import java.util.*
import kotlin.concurrent.thread


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PreviousTransactionsFragment : Fragment() {

    var lastTransactions = arrayOf<Transaction>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_previous_transactions, container, false)
        val listView = v.findViewById<ListView>(R.id.transactionListView)
        val totalSpentTextView = v.findViewById<TextView>(R.id.totalAmountSpentTextView);
        thread {
            val userData = getUserData()
            lastTransactions = userData.lastTransactions.toTypedArray()
        }
        var totalSpent = calculateTotalSpent(lastTransactions);
        totalSpentTextView.text = "Total: $totalSpent eur"
        listView.adapter =
            TransactionAdapter(requireContext(), lastTransactions)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                val intent = Intent(context, TransactionDetailActivity::class.java)
                intent.putExtra("transaction", lastTransactions[index])
                startActivity(intent)
            }
        return v

    }

    private fun calculateTotalSpent(transactions: Array<Transaction>): Double {
        var total = 0.0
        for (transaction in transactions) {
            for (product in transaction.products) {
                total += product.price
            }
        }
        return total
    }
}