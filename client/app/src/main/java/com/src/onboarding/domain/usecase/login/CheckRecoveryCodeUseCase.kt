package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.state.login.CodeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class CheckRecoveryCodeUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(code: String, email: String): CodeState = withContext(Dispatchers.IO) {
        return@withContext loginRepository.checkRecoveryCode(
            code = code, email = email.lowercase(
                Locale.getDefault()
            )
        )
    }
}