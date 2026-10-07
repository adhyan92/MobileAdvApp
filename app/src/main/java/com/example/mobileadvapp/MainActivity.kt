package com.example.mobileadvapp

import android.os.Bundle
import com.example.mobileadvapp.ui.DetailActivity
import android.net.Uri
import android.content.Intent
import android.util.Log
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
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_NAME", "Mahasiswa AMIKOM")
                putExtra("EXTRA_SCORE", counter)
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
        Log.d(TAG, "onSaveInstanceState Dipanggil - Counter " +
                "Disimpan: $counter")
    }
}

