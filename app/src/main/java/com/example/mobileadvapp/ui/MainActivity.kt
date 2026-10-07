package com.example.mobileadvapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileadvapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private val TAG = "LifecycleApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate Dipanggil")

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("KEY_COUNTER", 0)
        }
        binding.tvCounter.text = counter.toString()

        binding.btnIncrement.setOnClickListener {
            counter++
            binding.tvCounter.text = counter.toString()
        }

        binding.btnOpenDetail.setOnClickListener {
            val nama = binding.etName.text.toString().trim()
            val nim = binding.etNim.text.toString().trim()
            val prodi = binding.etProdi.text.toString().trim()

            if (nama.isEmpty() || nim.isEmpty() || prodi.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_NAME, nama)
                putExtra(DetailActivity.EXTRA_NIM, nim)
                putExtra(DetailActivity.EXTRA_PRODI, prodi)
                putExtra(DetailActivity.EXTRA_SCORE, counter)
            }
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Dipanggil")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Dipanggil")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Dipanggil")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Dipanggil")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Dipanggil")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_COUNTER", counter)
        Log.d(TAG, "onSaveInstanceState Dipanggil - Counter Disimpan: $counter")
    }
}