package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginAsGuestUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute() = withContext(Dispatchers.IO){
        loginRepository.setIsActiveAndClearSession()
    }
}