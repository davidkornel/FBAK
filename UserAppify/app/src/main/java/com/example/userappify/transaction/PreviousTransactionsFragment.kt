package com.example.userappify.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.userappify.AuthenticatedActivity
import com.example.userappify.R
import com.example.userappify.api.getUserData
import com.example.userappify.api.registerUser
import com.example.userappify.auth.AuthManager
import com.example.userappify.databinding.FragmentVoucherBinding
import com.example.userappify.deconding_utils.signData
import com.example.userappify.model.*
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.thread


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PreviousTransactionsFragment : Fragment() {

    var lastTransactions = arrayOf<Transaction>()

    fun updateView() {
        val listView = requireView().findViewById<ListView>(R.id.transactionListView)
        val totalSpentTextView = requireView().findViewById<TextView>(R.id.totalAmountSpentTextView);
        var totalSpent = calculateTotalSpent(lastTransactions);
        totalSpentTextView.text = "Total: ${String.format("%.2f", totalSpent)} eur"
        listView.adapter =
            TransactionAdapter(requireContext(), lastTransactions)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                val intent = Intent(context, TransactionDetailActivity::class.java)
                intent.putExtra("transaction", lastTransactions[index])
                startActivity(intent)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_previous_transactions, container, false)
        val listView = v.findViewById<ListView>(R.id.transactionListView)
        val totalSpentTextView = v.findViewById<TextView>(R.id.totalAmountSpentTextView);
        var totalSpent = calculateTotalSpent(lastTransactions);
        totalSpentTextView.text = "Total: ${String.format("%.2f", totalSpent)} eur"
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = activity?.let { AuthManager(it) }
        val userId = auth!!.getLoginUser()!!.id.toString()
        val userIdToSign = UserDataRequestToSign(userId)
        Log.d("idToJson",Gson().toJson(userIdToSign).toString())
        val signedContent = signData(Gson().toJson(userIdToSign).toString())
        var userDataRequest = UserDataRequest(signedContent,userId)

        this.context?.let { it1 ->
            getUserData(userDataRequest, it1, onResponse = this::onResponse, view)
        }
    }
    private fun onResponse(response: JSONObject, request: UserDataRequest) {
        val userDataResponse = Gson().fromJson(response.toString(),UserDataResponse::class.java)
        lastTransactions = userDataResponse.lastTransactions.toTypedArray()
        updateView()
    }

    private fun calculateTotalSpent(transactions: Array<Transaction>): Double {
        var total = 0.0
        for (transaction in transactions) {
            total += transaction.totalPaid
        }
        return total
    }
}