package com.src.onboarding.domain.usecase.user

import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNewQuestionUseCase(private val userRepository: UserRepository) {
    suspend fun execute(text: String): BasicState<Unit> = withContext(Dispatchers.IO) {
        return@withContext userRepository.addNewQuestion(text)
    }
}