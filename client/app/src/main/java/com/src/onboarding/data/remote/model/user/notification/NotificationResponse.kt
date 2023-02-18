package com.src.onboarding.data.remote.model.user.notification

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
class NotificationResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String
)