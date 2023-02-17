package com.src.onboarding.data.remote.dataSource.login

import com.src.onboarding.data.remote.model.login.login.LoginMapper
import com.src.onboarding.data.remote.model.login.loginAnswer.LoginAnswerResponse
import com.src.onboarding.data.remote.service.LoginService
import com.src.onboarding.data.remote.session.SessionStorage
import com.src.onboarding.data.remote.utils.ErrorMessage
import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class LoginDataSourceImpl(
    private val loginService: LoginService,
    private val sessionStorage: SessionStorage,
    private val loginMapper: LoginMapper
) : LoginDataSource {
    override suspend fun signIn(data: Login): LoginState {
        val registrationAnswerResponse =
            loginService.signIn(loginMapper.mapFromModelToResponse(data))
        if (registrationAnswerResponse.isSuccessful) {
            if (registrationAnswerResponse.body() != null) {
                val body = registrationAnswerResponse.body()!!
                sessionStorage.refresh(
                    refreshToken = body.refreshToken,
                    expireTimeRefreshToken = body.expireTimeRefreshToken,
                    accessToken = body.accessToken,
                    expireTimeAccessToken = body.expireTimeAccessToken,
                    id = body.id,
                    email = body.email
                )
                sessionStorage.setIsActive(true)
            }
            return LoginState.SuccessState
        } else {
            if (registrationAnswerResponse.code() == 404) {
                val errorMessage = ErrorMessage<LoginAnswerResponse>()
                val message = errorMessage.getErrorMessage(registrationAnswerResponse)
                if (message == ERROR_PASSWORD) {
                    return LoginState.ErrorPasswordState
                } else if (message == ERROR_EMAIL) {
                    return LoginState.ErrorEmailState
                }
            }
            return LoginState.ErrorState
        }
    }

    override suspend fun checkEmailExists(email: String): BasicState<Boolean> {
        val response = loginService.checkEmailExists(email)
        if (response.isSuccessful) {
            val body = response.body()?.exists
            if (body != null) {
                return BasicState
                    .SuccessState(body)
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun checkRecoveryCode(code: String, email: String): CodeState {
        val response = loginService.checkRecoveryCode(code, email)
        if (response.isSuccessful) {
            if (response.body() != null) {
                val body = response.body()!!
                sessionStorage.refresh(
                    refreshToken = body.refreshToken,
                    expireTimeRefreshToken = body.expireTimeRefreshToken,
                    accessToken = body.accessToken,
                    expireTimeAccessToken = body.expireTimeAccessToken,
                    id = body.id,
                    email = body.email
                )
                sessionStorage.setIsActive(true)
            }
            return CodeState.SuccessState
        } else {
            if (response.code() == 409) {
                val errorMessage = ErrorMessage<LoginAnswerResponse>()
                val message = errorMessage.getErrorMessage(response)
                if (message == INVALID_CODE) {
                    return CodeState.WrongCodeState
                }
            }
        }
        return CodeState.ErrorState
    }

    override suspend fun sendCodeForRecoveryPassword(email: String): CodeState {
        val response = loginService.sendCodeForRecoveryPassword(email)
        if (response.isSuccessful) {
            return CodeState.SuccessState
        }
        if (response.code() == 404) {
            return CodeState.WrongEmailState
        }
        return CodeState.ErrorState
    }

    override suspend fun registration(data: String, file: File?): RegistrationState {
        val dataMultipart = data.toRequestBody("text/plain".toMediaTypeOrNull())
        var part: MultipartBody.Part? = null
        if (file != null) {
            val fileMultipart = file.asRequestBody("image/*".toMediaTypeOrNull())
            part = MultipartBody.Part.createFormData("file", file.name, fileMultipart)
        }

        val response = loginService.signUp(dataMultipart, part)

        if (response.isSuccessful) {
            val body = response.body()!!
            sessionStorage.refresh(
                refreshToken = body.refreshToken,
                expireTimeRefreshToken = body.expireTimeRefreshToken,
                accessToken = body.accessToken,
                expireTimeAccessToken = body.expireTimeAccessToken,
                id = body.id,
                email = body.email
            )
            return RegistrationState.SuccessState
        } else {
            if (response.code() == 409) {
                val errorMessage = ErrorMessage<LoginAnswerResponse>()
                val message = errorMessage.getErrorMessage(response)
                if (message == EMAIL_ALREADY_IN_USE) {
                    return RegistrationState.EmailAlreadyExistsState
                }
            }
        }
        return RegistrationState.ErrorState
    }

    override suspend fun checkRecoveryCodeForAccountConfirmations(
        code: String,
        email: String
    ): CodeState {
        val response = loginService.checkRecoveryCodeForAccountConfirmations(code, email)
        if (response.isSuccessful) {
            sessionStorage.setIsActive(true)
            return CodeState.SuccessState
        }
        if (response.code() == 404) {
            return CodeState.WrongCodeState
        }
        return CodeState.ErrorState
    }

    override suspend fun sendCodeForAccountConfirmations(): BasicState<Unit> {
        val id = sessionStorage.getId()
        if (id.isNotEmpty()) {
            val response = loginService.sendCodeForAccountConfirmations(id.toLong())
            if (response.isSuccessful) {
                return BasicState.SuccessState(Unit)
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun recoveryPassword(email: String, password: String): ChangePasswordState {
        val response = loginService.recoveryPassword(email = email, password = password)
        if (response.isSuccessful) {
            val body = response.body()!!
            sessionStorage.refresh(
                refreshToken = body.email,
                expireTimeRefreshToken = body.expireTimeRefreshToken,
                accessToken = body.accessToken,
                expireTimeAccessToken = body.expireTimeAccessToken,
                id = body.id,
                email = body.email
            )
            return ChangePasswordState.SuccessState
        }
        if (response.code() == 404) {
            return ChangePasswordState.ErrorCodeState
        }
        return ChangePasswordState.ErrorState
    }

    private companion object {
        const val ERROR_PASSWORD = "Wrong password!"
        const val ERROR_EMAIL = "The user with such email does not exist."
        const val INVALID_CODE = "Invalid code"
        const val EMAIL_ALREADY_IN_USE = "This email is already in use"
    }
}