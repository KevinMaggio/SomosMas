package com.alkemy.ongsomosmas.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MemberResponse(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("deleted_at") val deletedAt: String?,
    val description: String,
    val facebookUrl: String,
    @SerializedName("group_id") val groupId: Int?,
    val id: Int,
    val image: String,
    val linkedinUrl: String,
    val name: String,
    @SerializedName("updated_at") val updatedAt: String
) : Serializable