package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class CheckEmailExistsUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(email: String): BasicState<Boolean> = withContext(Dispatchers.IO) {
        return@withContext loginRepository.checkEmailExists(email.lowercase(Locale.getDefault()))
    }
}