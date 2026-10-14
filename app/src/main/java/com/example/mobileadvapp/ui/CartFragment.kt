package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileadvapp.R
import com.example.mobileadvapp.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val args: CartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hargaSatuan = 12_000_000
        val total = hargaSatuan * args.quantity
        val fmt = NumberFormat.getInstance(Locale("id", "ID"))

        binding.tvCart.text = """
            Nama Produk: ${args.productName}
            Jumlah: ${args.quantity} unit
            Harga per Unit: Rp ${fmt.format(hargaSatuan)}

            Total Estimasi Harga: Rp ${fmt.format(total)}
        """.trimIndent()

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_categoryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}