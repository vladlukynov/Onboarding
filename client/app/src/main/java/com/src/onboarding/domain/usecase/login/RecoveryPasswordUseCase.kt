package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class RecoveryPasswordUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(email: String, password: String) = withContext(Dispatchers.IO) {
        return@withContext loginRepository.recoveryCode(
            email = email.lowercase(Locale.getDefault()),
            password = password
        )
    }
}