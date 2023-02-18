package com.src.onboarding.domain.usecase.login

import com.src.onboarding.domain.repository.LoginRepository

class GetPostIdUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(): Long? {
        return loginRepository.getPostId()
    }
}