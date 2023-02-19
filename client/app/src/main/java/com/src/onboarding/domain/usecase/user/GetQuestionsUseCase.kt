package com.src.onboarding.domain.usecase.user

import com.src.onboarding.domain.model.user.Question
import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetQuestionsUseCase(private val userRepository: UserRepository) {
    suspend fun execute(isCompleted: Boolean): BasicState<List<Question>> = withContext(Dispatchers.IO) {
        return@withContext userRepository.getQuestions(isCompleted)
    }
}