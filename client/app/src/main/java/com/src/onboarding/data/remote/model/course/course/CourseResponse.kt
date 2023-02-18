package com.src.onboarding.data.remote.model.course.course

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class CourseResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("photoSrc")
    val image: String?,
    @SerializedName("closed")
    val closed: Boolean,
    @SerializedName("countThemes")
    val countThemes: Int,
    @SerializedName("percentageOfCompletion")
    val percentageOfCompletion: Double?
)