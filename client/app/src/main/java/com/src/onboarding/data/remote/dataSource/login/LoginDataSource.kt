package com.src.onboarding.data.remote.dataSource.login

import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.domain.state.login.LoginState
import com.src.onboarding.domain.state.login.RegistrationState
import java.io.File

interface LoginDataSource {
    suspend fun signIn(data: Login): LoginState
    suspend fun checkEmailExists(email: String): BasicState<Boolean>
    suspend fun checkRecoveryCode(code: String, email: String): CodeState
    suspend fun sendCodeForRecoveryPassword(email: String): CodeState
    suspend fun registration(data: String, file: File?): RegistrationState
    suspend fun checkRecoveryCodeForAccountConfirmations(code: String, email: String): CodeState
    suspend fun sendCodeForAccountConfirmations(): BasicState<Unit>
}