package com.src.onboarding.data.repository

import com.src.onboarding.data.local.repository.LocalUserRepository
import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.login.ChangePasswordState
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.domain.state.login.RegistrationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val userLocalRepository: LocalUserRepository
) : LoginRepository {
    override suspend fun signIn(data: Login) = withContext(Dispatchers.IO) {
        return@withContext loginDataSource.signIn(data)
    }

    override suspend fun checkEmailExists(email: String): BasicState<Boolean> =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.checkEmailExists(email)
        }

    override suspend fun checkRecoveryCode(code: String, email: String): CodeState =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.checkRecoveryCode(code = code, email = email)
        }

    override suspend fun sendCodeForRecoveryPassword(email: String): CodeState =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.sendCodeForRecoveryPassword(email)
        }

    override suspend fun registration(data: String, file: File?): RegistrationState =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.registration(data, file)
        }

    override suspend fun checkRecoveryCodeForAccountConfirmations(
        code: String,
        email: String
    ): CodeState = withContext(Dispatchers.IO) {
        return@withContext loginDataSource.checkRecoveryCodeForAccountConfirmations(code, email)
    }

    override suspend fun sendCodeForAccountConfirmations(): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.sendCodeForAccountConfirmations()
        }

    override suspend fun setIsActiveAndClearSession() = withContext(Dispatchers.IO) {
        userLocalRepository.setIsActiveAndClearSession()
    }

    override suspend fun recoverPassword(password: String): ChangePasswordState =
        withContext(Dispatchers.IO) {
            return@withContext loginDataSource.recoverPassword(newPassword = password)
        }

    override suspend fun getPostId(): Long? {
        return userLocalRepository.getPostId()
    }
}