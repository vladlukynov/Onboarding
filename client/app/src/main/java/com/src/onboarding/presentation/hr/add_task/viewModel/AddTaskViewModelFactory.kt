package com.src.onboarding.presentation.hr.add_task.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.task.AddTaskUseCase
import com.src.onboarding.domain.usecase.user.GetIdUseCase
import javax.inject.Inject

class AddTaskViewModelFactory @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val getIdUseCase: GetIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskViewModel(
            addTaskUseCase = addTaskUseCase,
            getIdUseCase = getIdUseCase
        ) as T
    }
}