package com.src.onboarding.domain.usecase.task

import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.repository.TaskRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTasksByIsCompletedUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(completed: Boolean): BasicState<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext taskRepository.getTasksByIsCompleted(
                completed = completed
            )
        }
}