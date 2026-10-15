package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private var kategoriDipilih: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buka/tutup dropdown
        binding.layoutHeaderProduk.setOnClickListener {
            setExpanded(binding.layoutKategori.visibility != View.VISIBLE)
        }

        // Pilih kategori (tidak langsung pindah halaman)
        val items = mapOf(
            binding.itemLaptop to "Laptop Gaming",
            binding.itemMouse to "Mouse Gaming",
            binding.itemKeyboard to "Keyboard Mechanical",
            binding.itemHeadset to "Headset Gaming",
            binding.itemMonitor to "Monitor",
            binding.itemMousepad to "Mousepad"
        )

        items.forEach { (itemView, nama) ->
            itemView.setOnClickListener {
                kategoriDipilih = nama
                binding.tvPilihProduk.text = nama
                binding.btnLanjut.isEnabled = true
                setExpanded(false)
            }
        }

        // Pindah halaman hanya saat tombol Lanjut diklik
        binding.btnLanjut.setOnClickListener {
            val kategori = kategoriDipilih
            if (kategori == null) {
                Toast.makeText(requireContext(), "Pilih produk dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findNavController().navigate(
                R.id.action_categoryFragment_to_productDetailFragment,
                bundleOf("kategori" to kategori)
            )
        }
    }

    private fun setExpanded(expanded: Boolean) {
        binding.layoutKategori.visibility = if (expanded) View.VISIBLE else View.GONE
        binding.tvArrow.text = if (expanded) "▲" else "▼"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}