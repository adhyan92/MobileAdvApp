package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.data.local.SettingPreferences
import com.example.mobileadvapp.data.local.dataStore
import com.example.mobileadvapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
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

        // Tombol login: simpan username & token acak lalu navigasi ke home_screen.xml setelah simpan selesai
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

            viewLifecycleOwner.lifecycleScope.launch {
                // Tunggu proses simpan ke DataStore selesai terlebih dahulu
                pref.saveUserSession(randomToken, username)
                Toast.makeText(requireContext(), "Login berhasil!", Toast.LENGTH_SHORT).show()

                if (isAdded && findNavController().currentDestination?.id == R.id.loginFragment) {
                    goToHomeScreen()
                }
            }
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
