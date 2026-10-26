package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.data.local.SettingPreferences
import com.example.mobileadvapp.data.local.dataStore
import com.example.mobileadvapp.databinding.HomeLoginBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeLoginFragment : Fragment() {

    private var _binding: HomeLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)

        // Verifikasi Auto-Login saat aplikasi pertama kali dibuka:
        // Jika token tersedia di DataStore, arahkan pengguna langsung ke Main/Home Screen
        viewLifecycleOwner.lifecycleScope.launch {
            val (token, _) = pref.getUserSession().first()
            if (token.isNotEmpty() && isAdded) {
                if (findNavController().currentDestination?.id == R.id.homeLoginFragment) {
                    findNavController().navigate(R.id.action_homeLoginFragment_to_homeScreen)
                }
            }
        }

        // Tombol Login/Masuk di home_login.xml -> menuju ke fragment_login.xml
        binding.btnLogin.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.homeLoginFragment) {
                findNavController().navigate(R.id.action_homeLoginFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
