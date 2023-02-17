package com.src.onboarding.data.remote.model.login.email

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
class EmailExistsResponse(
    @SerializedName("exists") val exists: Boolean
)