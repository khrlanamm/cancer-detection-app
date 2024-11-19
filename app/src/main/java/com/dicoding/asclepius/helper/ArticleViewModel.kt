package com.dicoding.asclepius.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.ArticleItem
import com.dicoding.asclepius.data.ArticleRepository

class ArticleViewModel : ViewModel() {
    private val articleRepository = ArticleRepository()
    private val _articleList = MutableLiveData<List<ArticleItem>>()
    val articleList: LiveData<List<ArticleItem>> = _articleList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchHealthNews() {
        _isLoading.value = true
        articleRepository.getHealthNews(
            onSuccess = { newsList ->
                _articleList.postValue(newsList)
                _isLoading.value = false
            },
            onFailure = { errorMessage ->
                _isLoading.value = false
            }
        )
    }
}

