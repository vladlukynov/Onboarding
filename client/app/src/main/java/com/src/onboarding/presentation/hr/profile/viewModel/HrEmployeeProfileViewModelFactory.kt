package com.src.onboarding.presentation.hr.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.task.GetTasksByUserIdUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import javax.inject.Inject

class HrEmployeeProfileViewModelFactory @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getTasksByUserIdUseCase: GetTasksByUserIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HrEmployeeProfileViewModel(
            getUserProfileUseCase = getUserProfileUseCase,
            getStartedCoursesForUserUseCase = getStartedCoursesForUserUseCase,
            getActivitiesUseCase = getActivitiesUseCase,
            getTasksByUserIdUseCase = getTasksByUserIdUseCase
        ) as T
    }
}