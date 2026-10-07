package com.example.mobileadvapp.ui

<<<<<<< HEAD
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
=======
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
>>>>>>> f64004b669bd371c38f9a8f7c406ab0252a0c10b
import com.example.mobileadvapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

<<<<<<< HEAD
    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_NIM = "EXTRA_NIM"
        const val EXTRA_PRODI = "EXTRA_PRODI"
        const val EXTRA_SCORE = "EXTRA_SCORE"
    }

=======
>>>>>>> f64004b669bd371c38f9a8f7c406ab0252a0c10b
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME) ?: "-"
        val nim = intent.getStringExtra(EXTRA_NIM) ?: "-"
        val prodi = intent.getStringExtra(EXTRA_PRODI) ?: "-"
        val score = intent.getIntExtra(EXTRA_SCORE, 0)

        binding.btnCallCenter.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:082280794940")
            }
            safeStart(dialIntent)
        }

        binding.tvNama.text = name
        binding.tvNim.text = nim
        binding.tvProdi.text = prodi
        binding.tvSkor.text = score.toString()

        binding.btnLokasi.setOnClickListener {
            val mapUri = Uri.parse("geo:-7.7599,110.4083?q=Universitas+AMIKOM+Yogyakarta")
            safeStart(Intent(Intent.ACTION_VIEW, mapUri))
        }

        binding.btnWebsite.setOnClickListener {
            safeStart(Intent(Intent.ACTION_VIEW, Uri.parse("https://amikom.ac.id")))
        }
    }

    private fun safeStart(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Tidak ada aplikasi yang dapat menangani aksi ini", Toast.LENGTH_SHORT).show()
        }
=======
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("EXTRA_NAME")
        val score = intent.getIntExtra("EXTRA_SCORE", 0)
        binding.tvDetailInfo.text = "Nama: $name\nSkor: $score"
>>>>>>> f64004b669bd371c38f9a8f7c406ab0252a0c10b
    }
}