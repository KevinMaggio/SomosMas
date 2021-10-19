package com.alkemy.ongsomosmas.data.activities

import com.alkemy.ongsomosmas.data.model.ActivitiesResponse
import com.alkemy.ongsomosmas.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ActivitiesService {
    @GET("api/activities")
    suspend fun activities(): Response<ApiResponse<List<ActivitiesResponse>>>
}