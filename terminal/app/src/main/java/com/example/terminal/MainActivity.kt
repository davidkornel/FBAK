package com.example.terminal

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.*

/* Utility top-level function */
fun byteArrayToHex(ba: ByteArray): String {
    val sb = StringBuilder(ba.size * 2)
    for (b in ba) sb.append(String.format("%02x", b))
    return sb.toString()
}

private const val ACTION_SCAN = "com.google.zxing.client.android.SCAN"
class MainActivity : AppCompatActivity() {

    private val btScanQr by lazy { findViewById<Button>(R.id.bt_scan_qr) }
    private val tv_error by lazy { findViewById<TextView>(R.id.tv_error_main) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btScanQr.setOnClickListener { scanQRCode() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun scanQRCode() {
        try {
            // Intent with filter ACTION_SCAN
            val intent = Intent(ACTION_SCAN).apply {
                putExtra("SCAN_MODE", "QR_CODE_MODE" ) }
            startActivityForResult(intent, 0)
        }
        catch (e: ActivityNotFoundException) {
            showDialog(this, "No Scanner Found", "Download a scanner code app?", "Yes", "No").show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent(this, CheckoutActivity::class.java)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT")
                if (contents != null) {
                    intent.putExtra("text",contents)
                    startActivity(intent)
                }
                else
                    tv_error.text = "CAN'T READ THE QR CODE !"

            }
        }
    }

    private fun showDialog(act: Activity, title: CharSequence, message: CharSequence, buttonYes: CharSequence, buttonNo: CharSequence): android.app.AlertDialog {
        val downloadDialog = android.app.AlertDialog.Builder(act)
        downloadDialog.setTitle(title)
        downloadDialog.setMessage(message)
        downloadDialog.setPositiveButton(buttonYes) { _, _ ->
            // possiamo passare package name per installare il qr code reader esterno se non già installato
            val uri = Uri.parse("market://search?q=pname:com.google.zxing.client.android")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            act.startActivity(intent)
        }
        downloadDialog.setNegativeButton(buttonNo, null)
        return downloadDialog.create()
    }
}
