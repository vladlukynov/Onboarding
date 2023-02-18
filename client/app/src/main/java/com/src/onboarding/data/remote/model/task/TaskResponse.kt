package com.src.onboarding.data.remote.model.task

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class TaskResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("dateStart")
    val dateStart: String,
    @SerializedName("deadlineDate")
    val deadlineDate: String,
    @SerializedName("header")
    val header: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("completed")
    val completed: Boolean
)