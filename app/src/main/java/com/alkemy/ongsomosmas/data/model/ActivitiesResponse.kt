package com.alkemy.ongsomosmas.data.model

import com.google.gson.annotations.SerializedName

data class ActivitiesResponse(
    val name: String,
    val description: String,
    val image: String,
    @SerializedName("created_at") val createdAt: String
)