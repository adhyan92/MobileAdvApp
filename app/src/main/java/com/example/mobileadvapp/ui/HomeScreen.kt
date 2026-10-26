package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.data.local.SettingPreferences
import com.example.mobileadvapp.data.local.dataStore
import com.example.mobileadvapp.databinding.HomeScreenBinding

class HomeScreen : Fragment() {

    private var _binding: HomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(pref)
        )[SettingViewModel::class.java]

        // Menampilkan data sesi pengguna (nama & token) secara dinamis
        settingViewModel.getUserSession().observe(viewLifecycleOwner) { (token, name) ->
            binding.tvWelcome.text = if (name.isNotEmpty()) "Selamat Datang, $name!" else "Selamat Datang!"
            binding.tvToken.text = if (token.isNotEmpty()) "Token Sesi: $token" else "Token Sesi: Tidak ada"
        }

        // Tombol Pengaturan Tema -> Navigasi ke Layar Pengaturan Tema
        binding.btnTema.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_themeSettingFragment)
        }

        // Tombol Lihat Kategori Produk
        binding.btnKategori.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_categoryFragment)
        }

        // Tombol Logout & Hapus Sesi -> kembali ke Home Login
        binding.btnLogout.setOnClickListener {
            settingViewModel.clearUserSession()
            findNavController().navigate(R.id.action_homeScreen_to_homeLoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Alias class untuk fleksibilitas pemanggilan nama HomeScreenFragment
typealias HomeScreenFragment = HomeScreen
