package com.src.onboarding.data.remote.model.login.login

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)