package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Terima kategori dari CategoryFragment
        val kategori = arguments?.getString("kategori") ?: "-"
        binding.tvKategori.text = kategori

        // Isi nama produk hanya saat pertama dibuat (tidak menimpa ketikan setelah layar diputar)
        if (savedInstanceState == null) {
            binding.edtProductName.setText(kategori)
        }

        binding.btnAddToCart.setOnClickListener {
            val name = binding.edtProductName.text.toString().trim()
            val qty = binding.edtQuantity.text.toString().toIntOrNull() ?: 1

            if (name.isEmpty() || qty <= 0) {
                Toast.makeText(
                    requireContext(),
                    "Nama dan jumlah harus diisi dengan benar",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val action = ProductDetailFragmentDirections
                .actionProductDetailFragmentToCartFragment(productName = name, quantity = qty)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}