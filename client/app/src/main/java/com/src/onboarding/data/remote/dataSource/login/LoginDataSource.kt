package com.src.onboarding.data.remote.dataSource.login

import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.LoginState

interface LoginDataSource {
    suspend fun signIn(data: Login): LoginState
}