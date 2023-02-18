package com.src.onboarding.data.remote.model.user.user_profile

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class UserProfileResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("post")
    val post: String?,
    @SerializedName("team")
    val team: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String
)