package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.state.login.*
import java.io.File

interface LoginRepository {
    suspend fun signIn(data: Login): LoginState
    suspend fun checkEmailExists(email: String): BasicState<Boolean>
    suspend fun checkRecoveryCode(code: String, email: String): CodeState
    suspend fun sendCodeForRecoveryPassword(email: String): CodeState
    suspend fun registration(data: String, file: File?): RegistrationState
    suspend fun checkRecoveryCodeForAccountConfirmations(code: String, email: String): CodeState
    suspend fun sendCodeForAccountConfirmations(): BasicState<Unit>
    suspend fun setIsActiveAndClearSession()
    suspend fun recoverPassword(password: String): ChangePasswordState
}