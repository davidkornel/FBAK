package com.example.userappify.qrreader

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.userappify.R
import com.example.userappify.basket.ProductAdapter
import com.example.userappify.databinding.FragmentQrReaderBinding
import com.example.userappify.model.ProductHashViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import com.example.userappify.deconding_utils.*
import com.example.userappify.model.NamedProduct
import java.nio.charset.StandardCharsets


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QrReaderFragment : Fragment() {

    private var _binding: FragmentQrReaderBinding? = null
    private lateinit var qrScanIntegrator: IntentIntegrator

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ProductHashViewModel by activityViewModels()

    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        productAdapter = ProductAdapter(requireContext(), viewModel.products.value as ArrayList<NamedProduct>,viewModel)
        _binding = FragmentQrReaderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.notifyDataSetChanged()
        }
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
                    // Converting the data to NamedProduct
                    val namProd = decodeQRCODE(result.contents.toByteArray(StandardCharsets.ISO_8859_1))

                    // Show values in UI.
                    if (namProd != null) {
                        viewModel.addHashedProduct(namProd)
                        binding.tvReadName.text = "${namProd.name} added to the cart!"
                        binding.tvReadPrice.text = "Price : ${namProd.price.toString()} €"
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}