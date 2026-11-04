package com.example.mobileadvapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.data.local.SettingPreferences
import com.example.mobileadvapp.data.local.dataStore
import com.example.mobileadvapp.databinding.HomeScreenBinding
import kotlinx.coroutines.launch

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

        settingViewModel.getUserSession().observe(viewLifecycleOwner) { (token, name) ->
            binding.tvWelcome.text = if (name.isNotEmpty()) "Selamat Datang, $name!" else "Selamat Datang!"
            binding.tvToken.text = if (token.isNotEmpty()) "Token Sesi: $token" else "Token Sesi: Tidak ada"
        }

        // Lihat Kategori Produk
        binding.btnKategori.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.homeScreen) {
                findNavController().navigate(R.id.action_homeScreen_to_categoryFragment)
            }
        }

        // Kelola Mata Kuliah (Room DB) -> CourseActivity
        binding.btnMataKuliah.setOnClickListener {
            startActivity(Intent(requireContext(), CourseActivity::class.java))
        }

        // Kelola Mahasiswa (Room DB) -> StudentActivity
        binding.btnMahasiswa.setOnClickListener {
            startActivity(Intent(requireContext(), StudentActivity::class.java))
        }

        // Pengaturan Tema
        binding.btnTema.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.homeScreen) {
                findNavController().navigate(R.id.action_homeScreen_to_themeSettingFragment)
            }
        }

        // Logout & Hapus Sesi
        binding.btnLogout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                pref.clearUserSession()
                if (isAdded && findNavController().currentDestination?.id == R.id.homeScreen) {
                    findNavController().navigate(R.id.action_homeScreen_to_homeLoginFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

typealias HomeScreenFragment = HomeScreen