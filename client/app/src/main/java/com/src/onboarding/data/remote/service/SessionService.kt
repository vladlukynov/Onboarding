package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.RefreshTokenResponse
import com.src.onboarding.data.remote.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//TODO добавить путь
interface SessionService {
    @POST("refresh-tokens")
    fun refreshTokens(
        @Body refreshTokenResponse: RefreshTokenResponse
    ): Call<TokenResponse>
}