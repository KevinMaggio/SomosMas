package com.alkemy.ongsomosmas.data

import com.alkemy.ongsomosmas.data.model.ApiResponse
import com.alkemy.ongsomosmas.data.model.RegisterResponse
import com.alkemy.ongsomosmas.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterService {
    @POST("api/register")
    suspend fun postUser(@Body user: User): Response<ApiResponse<RegisterResponse>>
}