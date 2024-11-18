package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.HistoryActivity
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    companion object {
        const val IMAGE_URI = "img_uri"
        const val TAG = "imagePicker"
        const val RESULT_TEXT = "result_text"
        const val REQUEST_HISTORY_UPDATE = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val imageUriString = intent.getStringExtra(IMAGE_URI)
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            displayImage(imageUri)

            val imageClassifierHelper = ImageClassifierHelper(
                contextValue = this,
                classifierListenerValue = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(errorMessage: String) {
                        Log.d(TAG, "Error: $errorMessage")
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        results?.let { showResults(it) }
                    }
                }
            )
            imageClassifierHelper.classifyImage(imageUri)
        } else {
            Log.e(TAG, "No image URI provided")
            finish()
        }
    }
    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Displaying image: $uri")
        binding.resultImage.setImageURI(uri)
    }

    private fun showResults(results: List<Classifications>) {
        val topResult = results[0]
        val label = topResult.categories[0].label
        val score = topResult.categories[0].score

        fun Float.formatToString(): String {
            return String.format("%.2f%%", this * 100)
        }
        binding.resultText.text = "$label ${score.formatToString()}"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}