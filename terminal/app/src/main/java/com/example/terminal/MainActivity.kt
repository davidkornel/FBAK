package com.example.terminal

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

class MainActivity : AppCompatActivity() {
    private val actionScan = "com.google.zxing.client.android.SCAN"
    private val tvTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val tvText by lazy { findViewById<TextView>(R.id.tv_text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        print("Inflating")
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_scan)
            scanQRCode()
        return true
    }


    private fun scanQRCode() {
        val dlgListener = { _: DialogInterface, _: Int ->
            val uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        try {
            val intent = Intent(actionScan).putExtra("SCAN_MODE", "QR_CODE_MODE")
            startActivityForResult(intent, 0)
        } catch (ex: ActivityNotFoundException) {
            showYesNoDialog("No Scanner Found", "Download a scanner code activity?", dlgListener)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT")
                if (contents != null)
                    decodeAndShow(contents.toByteArray(StandardCharsets.ISO_8859_1))
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
        tvTitle.setText(R.string.items_list_title)

        val textTag = """
                   Read Tag (${clearTextTag.size}):
                   ${byteArrayToHex(clearTextTag)}
                   ID: $id
                   Name: $name
                   Price: €$euros.$cents
                  """.trimIndent()
        tvText.text = textTag
    }

    private fun showYesNoDialog(title: String, message: String, listener: (DialogInterface, Int) -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes", listener)
            .setNegativeButton("No", null)
            .show()
    }
}
