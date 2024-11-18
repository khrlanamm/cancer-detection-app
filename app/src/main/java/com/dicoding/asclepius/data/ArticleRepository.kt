package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.remote.response.ArticleResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiClient
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {
    fun getHealthNews(
        onSuccess: (List<ArticleItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        ApiClient.articleApiService.searchHealthNews("cancer", "health", "us", ApiConfig.API_KEY)
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        val articleList = articles.mapNotNull { article ->
                            if (!article.title.isNullOrEmpty() && !article.urlToImage.isNullOrEmpty() && !article.url.isNullOrEmpty() && !article.description.isNullOrEmpty()) {
                                ArticleItem(article.title, article.description, article.urlToImage, article.url)
                            } else {
                                null
                            }
                        }
                        onSuccess(articleList)
                    } else {
                        onFailure("Failed to fetch news")
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    onFailure(t.message ?: "Unknown error")
                }
            })
    }
}
