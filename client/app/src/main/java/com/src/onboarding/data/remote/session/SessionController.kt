package com.src.onboarding.data.remote.session

import com.src.onboarding.data.remote.model.token.RefreshTokenResponse
import com.src.onboarding.data.remote.service.SessionService

class SessionController(
    private val sessionService: SessionService,
    private val sessionStorage: SessionStorage
) {
    fun test(): String {
      return sessionStorage.getAccessToken()

    }

    fun getToken(): String? {
        if (sessionStorage.getAccessToken().isEmpty()) {
            return null
        } else {
            if (!sessionStorage.accessTokenIsValid()) {
                val needToRefreshToken = !sessionStorage.refreshTokenIsValid()
                val sessionResponse = sessionService.refreshTokens(
                    RefreshTokenResponse(
                        generateRefreshToken = needToRefreshToken,
                        email = sessionStorage.getEmail(),
                        accessToken = sessionStorage.getAccessToken(),
                        refreshToken = sessionStorage.getRefreshToken()
                    )
                ).execute().body()
                sessionResponse?.let {
                    sessionStorage.refreshAccessToken(
                        sessionResponse.accessToken,
                        it.expireTimeAccessToken
                    )
                    sessionResponse.refreshToken?.let { it1 ->
                        sessionResponse.expireTimeRefreshToken?.let { it2 ->
                            sessionStorage.refreshRefreshToken(
                                it1,
                                it2
                            )
                        }
                    }
                }
            }
            return "${SessionStorageImpl.TOKEN_TYPE} ${sessionStorage.getAccessToken()}"
        }
    }
}