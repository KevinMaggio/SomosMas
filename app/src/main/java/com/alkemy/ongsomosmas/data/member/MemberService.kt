package com.alkemy.ongsomosmas.data.member

import com.alkemy.ongsomosmas.data.model.ApiResponse
import com.alkemy.ongsomosmas.data.model.MemberResponse
import retrofit2.Response
import retrofit2.http.GET

interface MemberService {
    @GET("api/members")
    suspend fun fetchMembers(): Response<ApiResponse<List<MemberResponse>>>

}