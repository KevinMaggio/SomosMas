package com.alkemy.ongsomosmas.data.news

import com.alkemy.ongsomosmas.data.model.ApiResponse
import com.alkemy.ongsomosmas.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {
    @GET("api/news")
    suspend fun news(): Response<ApiResponse<List<NewsResponse>>>
}