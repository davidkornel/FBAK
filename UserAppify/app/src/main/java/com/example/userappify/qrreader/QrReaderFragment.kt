package com.example.userappify.qrreader

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.userappify.R
import com.example.userappify.databinding.FragmentQrReaderBinding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject



/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QrReaderFragment : Fragment() {

    private var _binding: FragmentQrReaderBinding? = null
    private lateinit var qrScanIntegrator: IntentIntegrator

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQrReaderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScanner()
        setOnClickListener()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setOrientationLocked(true)

    }

    private fun setOnClickListener() {
        binding.btnScan.setOnClickListener { performAction() }
    }

    private fun performAction() {
        qrScanIntegrator.initiateScan()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {

                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    binding.name.text = obj.getString("name")
                    binding.siteName.text = obj.getString("site_name")

                } catch (e: JSONException) {
                    e.printStackTrace()
                    println(result.contents)
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}