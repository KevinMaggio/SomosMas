package com.alkemy.ongsomosmas.data.welcome

import com.alkemy.ongsomosmas.data.model.ApiResponse
import com.alkemy.ongsomosmas.data.model.WelcomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface WelcomeService {
    @GET("api/slides")
    suspend fun welcome(): Response<ApiResponse<List<WelcomeResponse>>>
}
