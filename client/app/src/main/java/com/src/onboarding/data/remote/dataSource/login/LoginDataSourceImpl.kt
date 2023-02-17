package com.src.onboarding.data.remote.dataSource.login

import com.src.onboarding.data.remote.model.login.login.LoginMapper
import com.src.onboarding.data.remote.model.login.loginAnswer.LoginAnswerResponse
import com.src.onboarding.data.remote.service.LoginService
import com.src.onboarding.data.remote.session.SessionStorage
import com.src.onboarding.data.remote.utils.ErrorMessage
import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.LoginState

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

    private companion object {
        const val ERROR_PASSWORD = "Wrong password!"
        const val ERROR_EMAIL = "The user with such email does not exist."
    }
}