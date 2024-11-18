package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.ArticleAdapter
import com.dicoding.asclepius.databinding.ActivityHomeBinding
import com.dicoding.asclepius.helper.ArticleViewModel
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private var show = false
    private lateinit var binding: ActivityHomeBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        installSplashScreen().setKeepOnScreenCondition { show }
        Handler(Looper.getMainLooper()).postDelayed({
            show = false
        }, 2000)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        articleRecyclerView = findViewById(R.id.ArticlesRecyclerView)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up Navigation Bar
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

        findViewById<TextView>(R.id.greetingsButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        initRecyclerView()

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewModel.fetchHealthNews()
        articleViewModel.articleList.observe(this, Observer { articleList ->
            articleAdapter.submitList(articleList)
        })
    }
    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.ArticlesRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun openNewsUrl(view: View) {
        val url = view.getTag(R.id.articleTitle) as? String
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
