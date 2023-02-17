package com.src.onboarding.data.remote.model.course.colleague

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class ColleagueResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("post")
    val post: String?
)