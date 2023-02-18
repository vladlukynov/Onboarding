package com.src.onboarding.presentation.courses.tasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.task.GetTasksByIsCompletedUseCase
import com.src.onboarding.domain.usecase.task.SetCompletedTaskUseCase
import javax.inject.Inject

class TasksViewModelFactory @Inject constructor(
    private val getTasksByIsCompletedUseCase: GetTasksByIsCompletedUseCase,
    private val setCompletedTaskUseCase: SetCompletedTaskUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(
            getTasksByIsCompletedUseCase = getTasksByIsCompletedUseCase,
            setCompletedTaskUseCase = setCompletedTaskUseCase
        ) as T
    }
}