package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.AnalyzeEntity
import com.dicoding.asclepius.helper.AnalyzeAdapter
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HistoryActivity : AppCompatActivity() {
    private lateinit var analyzeRecyclerView: RecyclerView
    private lateinit var analyzeAdapter: AnalyzeAdapter
    private var analyzeList: MutableList<AnalyzeEntity> = mutableListOf()
    private lateinit var tvNotFound: TextView

    companion object{
        const val TAG = "analyzedata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_history)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<NavigationBarView>(R.id.menuBar).apply {
            selectedItemId = R.id.history_menu
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@HistoryActivity,
                            Intent(applicationContext, HomeActivity::class.java)
                        )
                        true
                    }

                    R.id.analyze_menu -> {
                        startActivityWithNavBarSharedTransition(
                            this@HistoryActivity,
                            Intent(applicationContext, MainActivity::class.java)
                        )
                        true
                    }

                    R.id.history_menu -> true
                    else -> false
                }
            }
        }
    }
}
