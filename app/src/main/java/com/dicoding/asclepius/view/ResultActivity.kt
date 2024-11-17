package com.dicoding.asclepius.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.HistoryActivity
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_result)

        findViewById<NavigationBarView>(R.id.menuBar).apply {
            selectedItemId = R.id.analyze_menu
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@ResultActivity,
                            Intent(applicationContext, HomeActivity::class.java)
                        )
                        true
                    }

                    R.id.analyze_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@ResultActivity,
                            Intent(applicationContext, MainActivity::class.java)
                        )
                        true
                    }

                    R.id.history_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@ResultActivity,
                            Intent(applicationContext, HistoryActivity::class.java)
                        )
                        true
                    }

                    else -> false
                }
            }
        }
        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
    }


}