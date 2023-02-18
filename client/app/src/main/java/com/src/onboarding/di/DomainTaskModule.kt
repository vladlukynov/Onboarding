package com.src.onboarding.di

import com.src.onboarding.domain.repository.TaskRepository
import com.src.onboarding.domain.usecase.task.AddTaskUseCase
import com.src.onboarding.domain.usecase.task.GetTasksByIsCompletedUseCase
import com.src.onboarding.domain.usecase.task.GetTasksByUserIdUseCase
import com.src.onboarding.domain.usecase.task.SetCompletedTaskUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainTaskModule {
    @Singleton
    @Provides
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(taskRepository = taskRepository)
    }

    @Singleton
    @Provides
    fun provideGetTasksByIsCompletedUseCase(taskRepository: TaskRepository): GetTasksByIsCompletedUseCase {
        return GetTasksByIsCompletedUseCase(taskRepository = taskRepository)
    }

    @Singleton
    @Provides
    fun provideGetTasksByUserIdUseCase(taskRepository: TaskRepository): GetTasksByUserIdUseCase {
        return GetTasksByUserIdUseCase(taskRepository = taskRepository)
    }

    @Singleton
    @Provides
    fun provideSetCompletedTaskUseCase(taskRepository: TaskRepository): SetCompletedTaskUseCase {
        return SetCompletedTaskUseCase(taskRepository = taskRepository)
    }
}