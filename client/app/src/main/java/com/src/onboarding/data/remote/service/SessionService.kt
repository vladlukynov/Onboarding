package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.token.RefreshTokenResponse
import com.src.onboarding.data.remote.model.token.TokenResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SessionService {
    @POST("${NetworkModule.USER_SERVICE_BASE_URL}/refresh-tokens")
    fun refreshTokens(
        @Body refreshTokenResponse: RefreshTokenResponse
    ): Call<TokenResponse>
}