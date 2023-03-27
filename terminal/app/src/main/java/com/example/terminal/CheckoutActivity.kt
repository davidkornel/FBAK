package com.example.terminal

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.concurrent.thread

class CheckoutActivity : AppCompatActivity() {

    private val tvCheckout by lazy { findViewById<TextView>(R.id.tv_check) }
    private val btPay by lazy { findViewById<Button>(R.id.bt_pay) }
    private val tvTitleCheckout by lazy { findViewById<TextView>(R.id.tv_title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        btPay.setOnClickListener {
            showConfirmDialog("Proceed to Payment","Are you sure to proceed to payment?")
        }

        val textV = intent.getStringExtra("text") ?: ""
        tvCheckout.text = textV
    }

    private fun showConfirmDialog(title: String, message: String) {
        val dlgListener = { _: DialogInterface, _: Int ->
            pay(this, checkout)
        }
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes", dlgListener)
            .setNegativeButton("Cancel", null)
            .show()
    }

    /*fun appendText(value: String) {
        runOnUiThread {
            val newValue = tvCheckout.text.toString() + "\n" + value
            tvCheckout.text = newValue
        }
    }*/

    fun writeText(value: String) {
        runOnUiThread { tvCheckout.text = value }
    }

    fun writeTitle(value: String) {
        runOnUiThread {
            tvTitleCheckout.text = value
        }
    }

    fun disableBtn() {
        runOnUiThread {
            btPay.apply {
                isEnabled = false
                isClickable = false
            }
        }
    }

    private fun decodeAndShow(clearTextTag: ByteArray) {

        val tag = ByteBuffer.wrap(clearTextTag)
        val tId = tag.int
        val id = UUID(tag.long, tag.long)
        val euros = tag.int
        val cents = tag.int
        val bName = ByteArray(tag.get().toInt())
        tag[bName]
        val name = String(bName, StandardCharsets.ISO_8859_1)

        val textTag = """
                   Read Tag (${clearTextTag.size}):
                   ${byteArrayToHex(clearTextTag)}
                   ID: $id
                   Name: $name
                   Price: €$euros.$cents
                  """.trimIndent()
        //tvMessage.text = textTag
    }
}