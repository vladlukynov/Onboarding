package com.src.onboarding.domain.usecase.user

import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetActivitiesUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): BasicState<List<Activity>> = withContext(Dispatchers.IO){
        return@withContext userRepository.getActivities()
    }
}