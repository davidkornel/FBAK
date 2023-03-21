package com.example.userappify.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.userappify.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TransactionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_detail_activity)
    }
}