package com.src.onboarding.domain.usecase.user

import com.src.onboarding.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetIdUseCase(private val userRepository: UserRepository) {
    suspend fun execute():Long = withContext(Dispatchers.IO){
        return@withContext  userRepository.getId()
    }
}