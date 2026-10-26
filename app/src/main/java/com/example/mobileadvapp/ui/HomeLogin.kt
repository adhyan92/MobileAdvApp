package com.example.mobileadvapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobileadvapp.R
import com.example.mobileadvapp.databinding.HomeLoginBinding

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

        // Tombol Login/Masuk di home_login.xml -> menuju ke fragment_login.xml
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_homeLoginFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
