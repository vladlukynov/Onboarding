package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.LoginState

interface LoginRepository {
    suspend fun signIn(data: Login): LoginState
}