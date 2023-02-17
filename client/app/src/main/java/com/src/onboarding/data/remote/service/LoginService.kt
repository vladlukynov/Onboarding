package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.login.email.EmailExistsResponse
import com.src.onboarding.data.remote.model.login.login.LoginResponse
import com.src.onboarding.data.remote.model.login.loginAnswer.LoginAnswerResponse
import com.src.onboarding.di.NetworkModule
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoginService {
    @Headers("Accept: application/json")
    @POST("${NetworkModule.USER_SERVICE_BASE_URL}/user/login")
    suspend fun signIn(@Body data: LoginResponse): Response<LoginAnswerResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/check-email-exists")
    suspend fun checkEmailExists(
        @Query("email", encoded = true) email: String
    ): Response<EmailExistsResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/password-recovery")
    suspend fun checkRecoveryCode(
        @Query("code") code: String,
        @Query("email", encoded = true) email: String
    ): Response<LoginAnswerResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/send-code-for-recovery-password")
    suspend fun sendCodeForRecoveryPassword(
        @Query("email", encoded = true) email: String
    ): Response<Unit>

    @Multipart
    @POST("${NetworkModule.USER_SERVICE_BASE_URL}/user/registration")
    suspend fun signUp(
        @Part("registerBeanString") data: RequestBody,
        @Part file: MultipartBody.Part?
    ): Response<LoginAnswerResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/confirm-account")
    suspend fun checkRecoveryCodeForAccountConfirmations(
        @Query("confirmationCode") code: String,
        @Query("email", encoded = true) email: String
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/send-code-for-confirmation-account")
    suspend fun sendCodeForAccountConfirmations(@Query("id") userId: Long): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/password-recovery")
    suspend fun recoveryPassword(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<LoginAnswerResponse>

}