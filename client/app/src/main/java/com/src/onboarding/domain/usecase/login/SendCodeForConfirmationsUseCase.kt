package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendCodeForConfirmationsUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(): BasicState<Unit> = withContext(Dispatchers.IO) {
        return@withContext loginRepository.sendCodeForAccountConfirmations()
    }
}