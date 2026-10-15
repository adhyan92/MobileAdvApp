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

    // Daftar harga per produk (kunci huruf kecil agar tidak sensitif huruf besar/kecil)
    private val daftarHarga = mapOf(
        "laptop gaming" to 12_000_000,
        "mouse gaming" to 199_000,
        "keyboard mechanical" to 299_000,
        "headset gaming" to 149_000,
        "monitor" to 3_750_000,
        "mousepad" to 80_000
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fmt = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"))
        val hargaSatuan = daftarHarga[args.productName.trim().lowercase()]

        binding.tvNamaProduk.text = args.productName
        binding.tvJumlah.text = "${args.quantity} unit"

        if (hargaSatuan != null) {
            val total = hargaSatuan * args.quantity
            binding.tvHargaSatuan.text = "Rp ${fmt.format(hargaSatuan)}"
            binding.tvTotal.text = "Rp ${fmt.format(total)}"
        } else {
            binding.tvHargaSatuan.text = "tidak ditemukan"
            binding.tvTotal.text = "-"
        }

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_categoryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}