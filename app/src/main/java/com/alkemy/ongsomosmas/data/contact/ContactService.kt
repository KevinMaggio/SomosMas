package com.alkemy.ongsomosmas.data.contact

import com.alkemy.ongsomosmas.data.model.ApiResponse
import com.alkemy.ongsomosmas.data.model.Contact
import com.alkemy.ongsomosmas.data.model.ContactResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactService {
    @POST("api/contacts")
    suspend fun contact(@Body contact: Contact): Response<ApiResponse<ContactResponse>>
}