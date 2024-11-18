package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun searchHealthNews(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("country") language: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleResponse>
}
