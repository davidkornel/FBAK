package com.example.userappify.basket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.userappify.R
import java.util.UUID

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_cart, container, false)
        val listView = v.findViewById<ListView>(R.id.list_view_products)
        listView.adapter = ProductAdapter(requireContext(), basket.products)
        v.findViewById<Button>(R.id.btn_checkout).setOnClickListener {
            val checkout = getCheckoutFromNamedProducts(
                basket.products,
                UUID.randomUUID(),
                UUID.randomUUID(),
                true,
                "signature"
            )
            // encode as json
            val coJson = encode(checkout)
            // TODO remove from the db when not using list
            //basket.products.removeAll(basket.products.toSet())
            val intent = Intent(context, ShowCodeActivity::class.java)
            intent.putExtra("CHECKOUT", coJson)
            startActivity(intent)
        }
        return v

    }
}