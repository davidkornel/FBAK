package com.example.userappify.basket

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.userappify.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.util.*
import kotlin.concurrent.thread

private const val SIZE = 600
private const val ISO_SET = "ISO-8859-1"

class ShowCodeActivity : AppCompatActivity() {
    private val tv_value by lazy { findViewById<TextView>(R.id.tv_content) }
    private val btn_back by lazy { findViewById<Button>(R.id.back_btn) }
    private var content = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_code)

        val image = findViewById<ImageView>(R.id.img_code)
        btn_back.setOnClickListener {
            this.finish()
        }
        val checkout = intent.getStringExtra("CHECKOUT") ?: ""


        thread {
            encodeAsBitmap(checkout).also { runOnUiThread { image.setImageBitmap(it) } }
        }
    }

    private fun encodeAsBitmap(str: String): Bitmap? {
        val result: BitMatrix
        val hints = Hashtable<EncodeHintType, String>().apply { put(EncodeHintType.CHARACTER_SET, ISO_SET) }
        var width = SIZE
        try {
            result = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, SIZE, hints)
        }
        catch (e: Exception) {
            content += "\n${e.message}"
            runOnUiThread { tv_value.text = content }
            return null
        }
        val w = result.width
        val h = result.height
        val colorDark = resources.getColor(R.color.black, null)
        val colorLight = resources.getColor(R.color.white, null)
        val pixels = IntArray(w * h)
        for (line in 0 until h) {
            val offset = line * w
            for (col in 0 until w)
                pixels[offset + col] = if (result.get(col, line)) colorDark else colorLight
        }

        runOnUiThread { tv_value.text = "Use this QRCode to pay and open the gate" }

        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).apply { setPixels(pixels, 0, w, 0, 0, w, h) }
    }
}