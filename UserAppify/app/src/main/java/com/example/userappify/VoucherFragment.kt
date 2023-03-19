package com.example.userappify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.userappify.databinding.FragmentVoucherBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class VoucherFragment : Fragment() {

    private var staticVouchers = arrayOf("1", "2")
    private var _binding: FragmentVoucherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val v = inflater.inflate(R.layout.fragment_voucher, container, false)
        val listView = v.findViewById<ListView>(R.id.voucher_list_view)
        listView.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, staticVouchers)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                Toast.makeText(
                    context,
                    "Clicked " + staticVouchers[index],
                    Toast.LENGTH_SHORT
                ).show()
            }
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.textviewFirst.onse {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}