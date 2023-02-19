package com.src.onboarding.data.remote.model.course.course_page.course

import com.google.gson.annotations.SerializedName
import com.src.onboarding.data.remote.model.course.course_page.block.BlockResponse

@kotlinx.serialization.Serializable
data class CourseBlockResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("countThemes")
    val countThemes: Int,
    @SerializedName("blocks")
    val blocks: List<BlockResponse>
)