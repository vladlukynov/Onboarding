package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.login.login.LoginResponse
import com.src.onboarding.data.remote.model.login.loginAnswer.LoginAnswerResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Accept: application/json")
    @POST("${NetworkModule.USER_SERVICE_BASE_URL}/user/login")
    suspend fun signIn(@Body data: LoginResponse): Response<LoginAnswerResponse>
}