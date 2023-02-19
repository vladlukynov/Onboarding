package com.src.onboarding.data.remote.model.course.course_page.block_item

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class BlockItemResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("condition")
    val condition: Int,
    @SerializedName("numberInBlock")
    val numberInBlock: Int
)