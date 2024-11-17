package com.dicoding.asclepius.ui.analyze

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.asclepius.R

class AnalyzeFragment : Fragment() {

    companion object {
        fun newInstance() = AnalyzeFragment()
    }

    private val viewModel: AnalyzeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_analyze, container, false)
    }
}