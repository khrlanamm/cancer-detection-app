package com.dicoding.asclepius.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import com.dicoding.asclepius.view.HistoryActivity
import com.example.bottomnavsampleapp.startActivityWithNavBarSharedTransition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    companion object {
        const val TAG = "ImagePicker"
        private const val REQUEST_RESULT = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage()
                moveToResult()
            } ?: run {
                showToast(getString(R.string.failed_clasifying))
            }
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data
            selectedImg?.let { uri ->
                currentImageUri = uri
                showImage()
                startUCrop(uri)
            } ?: showToast("Failed to get image URI")
        }
    }

    private fun startUCrop(sourceUri: Uri) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(cacheDir, fileName))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                showCroppedImage(resultUri)
            } ?: showToast("Failed to crop image")
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast("Crop error: ${cropError?.message}")
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d(TAG, "Displaying image: $uri")
            binding.previewImageView.setImageURI(uri)
        } ?: Log.d(TAG, "No image to display")
    }

    private fun analyzeImage() {
        val intent = Intent(this, ResultActivity::class.java)
        croppedImageUri?.let { uri ->
            intent.putExtra(ResultActivity.IMAGE_URI, uri.toString())
            startActivityForResult(intent, REQUEST_RESULT)
        } ?: showToast(getString(R.string.failed_clasifying))
    }

    private fun moveToResult() {
        Log.d(TAG, "Moving to ResultActivity")
        val intent = Intent(this, ResultActivity::class.java)
        croppedImageUri?.let { uri ->
            intent.putExtra(ResultActivity.IMAGE_URI, uri.toString())
            startActivity(intent)
        } ?: showToast(getString(R.string.failed_clasifying))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showCroppedImage(uri: Uri) {
        binding.previewImageView.setImageURI(uri)
        croppedImageUri = uri
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}