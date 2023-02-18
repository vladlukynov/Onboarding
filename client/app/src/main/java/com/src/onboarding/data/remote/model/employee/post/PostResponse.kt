package com.src.onboarding.data.remote.model.employee.post

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class PostResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)