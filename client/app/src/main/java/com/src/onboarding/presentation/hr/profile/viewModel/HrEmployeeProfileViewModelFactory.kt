package com.src.onboarding.presentation.hr.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetStartedCoursesByIdForUserUseCase
import com.src.onboarding.domain.usecase.task.GetTasksByUserIdUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserByIdUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import javax.inject.Inject

class HrEmployeeProfileViewModelFactory @Inject constructor(
    private val getUserProfileUseCase: GetUserByIdUseCase,
    private val getStartedCoursesByIdForUserUseCase: GetStartedCoursesByIdForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getTasksByUserIdUseCase: GetTasksByUserIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HrEmployeeProfileViewModel(
            getUserByIdUseCase = getUserProfileUseCase,
            getStartedCoursesByIdForUserUseCase = getStartedCoursesByIdForUserUseCase,
            getActivitiesUseCase = getActivitiesUseCase,
            getTasksByUserIdUseCase = getTasksByUserIdUseCase
        ) as T
    }
}