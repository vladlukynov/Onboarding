package com.src.onboarding.data.remote.model.user.activity

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class ActivityResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("percent")
    val percent: String?
)