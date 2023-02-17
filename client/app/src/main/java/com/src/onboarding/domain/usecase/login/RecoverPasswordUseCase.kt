package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecoverPasswordUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(password: String) = withContext(Dispatchers.IO) {
        return@withContext loginRepository.recoverPassword(
            password = password
        )
    }
}