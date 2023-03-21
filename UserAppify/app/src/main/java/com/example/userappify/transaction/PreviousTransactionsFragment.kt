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
import com.example.userappify.databinding.FragmentVoucherBinding
import com.example.userappify.model.NamedProduct
import com.example.userappify.model.Transaction
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PreviousTransactionsFragment : Fragment() {

    //    TODO fetch data
    private var staticTransactions =
        arrayOf(
            Transaction(
                UUID.randomUUID(),
                listOf(
                    NamedProduct(UUID.randomUUID(), 22.0, "Butter"),
                    NamedProduct(UUID.randomUUID(), 81.3, "Mailbox")
                ),
                null,
                UUID.randomUUID(), ""
            ),
            Transaction(
                UUID.randomUUID(),
                listOf(NamedProduct(UUID.randomUUID(), 12.0, "Hat")),
                null,
                UUID.randomUUID(), ""
            )
        )
    private var _binding: FragmentVoucherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_previous_transactions, container, false)
        val listView = v.findViewById<ListView>(R.id.transactionListView)
        val totalSpentTextView = v.findViewById<TextView>(R.id.totalAmountSpentTextView);
        var totalSpent = calculateTotalSpent(staticTransactions);
        totalSpentTextView.text = "Total: $totalSpent eur"
        listView.adapter =
            TransactionAdapter(requireContext(), staticTransactions)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                val intent = Intent(context, TransactionDetailActivity::class.java)
                intent.putExtra("EXTRA_SESSION_ID", "transaction index: $index")
                startActivity(intent)
            }
        return v

    }

    private fun calculateTotalSpent(transactions: Array<Transaction>): Double {
        var total = 0.0
        for ( transaction in transactions) {
            for (product in transaction.products){
                total += product.price
            }
        }
        return total
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.textviewFirst.onse {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}