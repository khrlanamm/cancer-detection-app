package com.dicoding.asclepius.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.view.HistoryActivity
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<NavigationBarView>(R.id.menuBar).apply {
            selectedItemId = R.id.analyze_menu
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@MainActivity,
                            Intent(applicationContext, HomeActivity::class.java)
                        )
                        true
                    }

                    R.id.history_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@MainActivity,
                            Intent(applicationContext, HistoryActivity::class.java)
                        )
                        true
                    }

                    R.id.analyze_menu -> true
                    else -> false
                }
            }
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


