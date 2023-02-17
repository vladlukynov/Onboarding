package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SignInUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(email: String, password: String) =
        withContext(Dispatchers.IO) {
            val data =
                Login(email = email.lowercase(Locale.getDefault()), password = password)
            return@withContext loginRepository.signIn(data)
        }
}