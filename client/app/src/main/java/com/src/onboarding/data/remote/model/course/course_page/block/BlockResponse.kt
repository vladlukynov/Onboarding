package com.src.onboarding.data.remote.model.course.course_page.block

import com.google.gson.annotations.SerializedName
import com.src.onboarding.data.remote.model.course.course_page.block_item.BlockItemResponse

@kotlinx.serialization.Serializable
data class BlockResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("itemsInBlock")
    val itemInBlock: List<BlockItemResponse>
)