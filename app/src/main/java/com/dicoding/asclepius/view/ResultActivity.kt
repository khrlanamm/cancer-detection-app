package com.dicoding.asclepius.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bottomNavigationView = findViewById(R.id.menuBar)

        bottomNavigationView.selectedItemId = R.id.analyze_menu
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.analyze_menu -> {
                    true
                }
                R.id.history_menu -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    true
                }
                else -> false
            }
        }
        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
    }


}