package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.view.HistoryActivity
import com.dicoding.asclepius.R
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private var show = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition{ show }
        Handler(Looper.getMainLooper()).postDelayed({
            show = false
        }, 2000)

        enableEdgeToEdge()

        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<NavigationBarView>(R.id.menuBar).apply {
            selectedItemId = R.id.home_menu
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.history_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@HomeActivity,
                            Intent(applicationContext, HistoryActivity::class.java)
                        )
                        true
                    }

                    R.id.analyze_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@HomeActivity,
                            Intent(applicationContext, MainActivity::class.java)
                        )
                        true
                    }

                    R.id.home_menu -> true
                    else -> false
                }
            }
        }
    }
}