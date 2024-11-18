package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.AnalyzeEntity
import com.dicoding.asclepius.data.local.room.AnalyzeDatabase
import com.dicoding.asclepius.helper.AnalyzeAdapter
import com.dicoding.asclepius.view.ResultActivity.Companion.REQUEST_HISTORY_UPDATE
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity(), AnalyzeAdapter.OnDeleteClickListener {
    private lateinit var analyzeRecyclerView: RecyclerView
    private lateinit var analyzeAdapter: AnalyzeAdapter
    private var analyzeList: MutableList<AnalyzeEntity> = mutableListOf()
    private lateinit var tvNotFound: TextView

    companion object {
        const val TAG = "analyzedata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_history)

        analyzeRecyclerView = findViewById(R.id.rvHistory)
        tvNotFound = findViewById(R.id.tvNotFound)

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
        analyzeRecyclerView = findViewById(R.id.rvHistory)
        tvNotFound = findViewById(R.id.tvNotFound)

        analyzeAdapter = AnalyzeAdapter(analyzeList)
        analyzeAdapter.setOnDeleteClickListener(this)
        analyzeRecyclerView.adapter = analyzeAdapter
        analyzeRecyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        GlobalScope.launch(Dispatchers.Main) {
            loadAnalyzeFromDatabase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_HISTORY_UPDATE && resultCode == RESULT_OK) {
            GlobalScope.launch(Dispatchers.Main) {
                loadAnalyzeFromDatabase()
            }
        }
    }

    private fun loadAnalyzeFromDatabase() {
        GlobalScope.launch(Dispatchers.Main) {
            val predictions =
                AnalyzeDatabase.getDatabase(this@HistoryActivity).analyzeDao().getAllPredictions()
            Log.d(TAG, "Number of predictions: ${predictions.size}")
            analyzeList.clear()
            analyzeList.addAll(predictions)
            analyzeAdapter.notifyDataSetChanged()
            showOrHideNoHistoryText()
        }
    }

    private fun showOrHideNoHistoryText() {
        if (analyzeList.isEmpty()) {
            tvNotFound.visibility = View.VISIBLE
            analyzeRecyclerView.visibility = View.GONE
        } else {
            tvNotFound.visibility = View.GONE
            analyzeRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDeleteClick(position: Int) {
        val prediction = analyzeList[position]
        if (prediction.result.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                AnalyzeDatabase.getDatabase(this@HistoryActivity).analyzeDao()
                    .deletePrediction(prediction)
            }
            analyzeList.removeAt(position)
            analyzeAdapter.notifyDataSetChanged()
            showOrHideNoHistoryText()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
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
}
