package com.src.onboarding.domain.usecase.task

import com.src.onboarding.domain.repository.TaskRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetCompletedTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(taskId: Long, completed: Boolean): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext taskRepository.setCompletedTask(
                taskId = taskId,
                completed = completed
            )
        }
}