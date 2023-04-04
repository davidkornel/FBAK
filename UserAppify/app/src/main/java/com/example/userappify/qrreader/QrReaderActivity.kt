package com.example.userappify.qrreader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userappify.databinding.ActivityQrreaderBinding
class QrReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrreaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrreaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}