package com.src.onboarding.presentation.courses.add_employee.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.employee.post.GetPostsUseCase
import com.src.onboarding.domain.usecase.employee.team.GetTeamsUseCase
import com.src.onboarding.domain.usecase.employee.worker.AddWorkerUseCase
import javax.inject.Inject

class AddEmployeeViewModelFactory @Inject constructor(
    private val addWorkerUseCase: AddWorkerUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getTeamsUseCase: GetTeamsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEmployeeViewModel(
            addWorkerUseCase = addWorkerUseCase,
            getPostsUseCase = getPostsUseCase,
            getTeamsUseCase = getTeamsUseCase
        ) as T
    }
}