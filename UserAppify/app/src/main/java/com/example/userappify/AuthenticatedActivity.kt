package com.example.userappify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userappify.basket.CartFragment
import com.example.userappify.databinding.ActivityAuthenticatedBinding
import com.example.userappify.qrreader.QrReaderFragment
import com.example.userappify.voucher.VoucherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class AuthenticatedActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityAuthenticatedBinding

    private var qrFragment = QrReaderFragment()
    private var previousTransactionsFragment = PreviousTransactionsFragment()
    private var voucherFragment = VoucherFragment()
    private var cartFragment = CartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.bottom_nav);
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_authenticated, qrFragment).commit()


        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_qr_reader -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_authenticated, qrFragment)
                        .commit()
                    return@OnItemSelectedListener true
                }
                R.id.nav_previous_transactions -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_authenticated, previousTransactionsFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.nav_vouchers -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_authenticated, voucherFragment).commit()
                    return@OnItemSelectedListener true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_authenticated, cartFragment).commit()
                    return@OnItemSelectedListener true
                }
            }
            false
        })
    }


}

private fun NavigationBarView.setOnItemReselectedListener(onItemSelectedListener: NavigationBarView.OnItemSelectedListener) {
    TODO("Not yet implemented")
}

