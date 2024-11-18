package com.dicoding.asclepius.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val articleApiService: ApiService = retrofit.create(ApiService::class.java)
}
