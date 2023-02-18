package com.src.onboarding.presentation.profile.user_profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import javax.inject.Inject

class UserProfileViewModelFactory @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(
            getUserProfileUseCase = getUserProfileUseCase,
            getStartedCoursesForUserUseCase = getStartedCoursesForUserUseCase,
            getActivitiesUseCase = getActivitiesUseCase
        ) as T
    }
}