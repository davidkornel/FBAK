package com.example.userappify.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.userappify.R
import com.example.userappify.api.getUserData
import com.example.userappify.auth.AuthManager
import com.example.userappify.databinding.FragmentVoucherBinding
import com.example.userappify.deconding_utils.signData
import com.example.userappify.model.*
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class VoucherFragment : Fragment() {
    var vouchers = arrayOf<Voucher>()

    private var _binding: FragmentVoucherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ProductHashViewModel by activityViewModels()
    private lateinit var voucherAdapter: VoucherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_voucher, container, false)
        val listView = v.findViewById<ListView>(R.id.voucherListView)
        voucherAdapter = VoucherAdapter(requireContext(), viewModel.vouchers.value as List<Voucher>)
        listView.adapter = voucherAdapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                if ( viewModel.vouchers.value?.get(index)?.isUsed == true){
                    Toast.makeText(
                        context,
                        "Clicked Voucher is already used",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    (listView.adapter as VoucherAdapter).setSelectedIndex(index)
                }
            }
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = activity?.let { AuthManager(it) }
        val userId = auth!!.getLoginUser()!!.id.toString()
        val signedContent = signData(Gson().toJson(userId).toString())
        var userDataRequest = UserDataRequest(signedContent,userId)
        this.context?.let { it1 ->
            getUserData(userDataRequest, it1, onResponse = this::onResponse, view)
        }
        viewModel.products.observe(viewLifecycleOwner) {
            voucherAdapter.notifyDataSetChanged()
        }
    }
    private fun onResponse(response: JSONObject, request: UserDataRequest) {
        val userDataResponse = Gson().fromJson(response.toString(), UserDataResponse::class.java)
        viewModel.updateVouchers(getVouchersFromIds(userDataResponse.availableVoucherIds))
        voucherAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getVouchersFromIds(voucherIds: List<String>): List<Voucher> {
        val result = mutableListOf<Voucher>()
        for (id in voucherIds) {
            result.add(Voucher(UUID.fromString(id)))
        }
        return result
    }
}