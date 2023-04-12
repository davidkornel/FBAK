package com.example.userappify.basket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.userappify.R
import com.example.userappify.auth.AuthManager
import com.example.userappify.model.NamedProduct
import com.example.userappify.model.ProductHashViewModel

class CartFragment : Fragment() {

    private val viewModel: ProductHashViewModel by activityViewModels()

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_cart, container, false)
        val cbDiscount = v.findViewById<CheckBox>(R.id.cb_discount)
        cbDiscount.isChecked = false
        val tvVoucher = v.findViewById<TextView>(R.id.tv_cart_voucher)
        tvVoucher.text = when (viewModel.getSelectedVoucher()) {
            null -> "No voucher selected"
            else -> "Voucher discount :"+ viewModel.getSelectedVoucher()?.discount.toString()+" %"
        }
        val listView = v.findViewById<ListView>(R.id.list_view_products)
        productAdapter = ProductAdapter(requireContext(), viewModel.products.value as ArrayList<NamedProduct>,viewModel)
        listView.adapter = productAdapter
        val auth = activity?.let { AuthManager(it) }
        v.findViewById<Button>(R.id.btn_checkout).setOnClickListener {
            val checkout = getCheckoutFromNamedProducts(
                viewModel.products.value as ArrayList<NamedProduct>,
                viewModel.getSelectedVoucher()?.id,
                auth!!.getLoginUser()!!.id,
                cbDiscount.isChecked,
                "signature"
//                getPubKey().toString()
            )
            // encode as json
            val coJson = encode(checkout)
            Log.d("DEBUG",coJson)
            // After the checkout the cart has to be empty
            viewModel.removeAllProd()
            productAdapter.notifyDataSetChanged()
            cbDiscount.isChecked = false

            val intent = Intent(context, ShowCodeActivity::class.java)
            intent.putExtra("CHECKOUT", coJson)
            startActivity(intent)
        }
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.notifyDataSetChanged()
        }
    }
}