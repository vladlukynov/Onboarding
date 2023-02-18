package com.src.onboarding.data.remote.model.employee.team

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class TeamResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)