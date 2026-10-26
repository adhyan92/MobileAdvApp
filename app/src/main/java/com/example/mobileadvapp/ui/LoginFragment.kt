package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.data.local.SettingPreferences
import com.example.mobileadvapp.data.local.dataStore
import com.example.mobileadvapp.databinding.FragmentLoginBinding
import java.util.UUID

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))
            .get(SettingViewModel::class.java)

        // Tombol login: simpan username & token acak lalu navigasi dari fragment_login.xml ke home_screen.xml
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Username dan password wajib diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Membuat token acak unik setiap kali pengguna melakukan login
            val randomToken = "token_" + UUID.randomUUID().toString().replace("-", "").take(12)

            // Simpan sesi login (token acak dan username) ke DataStore Preferences
            settingViewModel.saveUserSession(randomToken, username)
            Toast.makeText(requireContext(), "Login berhasil!", Toast.LENGTH_SHORT).show()

            // Navigasi ke home_screen.xml
            goToHomeScreen()
        }
    }

    private fun goToHomeScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_homeScreen)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
