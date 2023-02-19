package com.src.onboarding.presentation.hr.your_profile.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.user.EditProfileUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import com.src.onboarding.domain.usecase.user.LogoutUseCase
import javax.inject.Inject

class HrUserProfileViewModelFactory @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HrUserProfileViewModel(
            getUserProfileUseCase = getUserProfileUseCase,
            getStartedCoursesForUserUseCase = getStartedCoursesForUserUseCase,
            getActivitiesUseCase = getActivitiesUseCase,
            editProfileUseCase = editProfileUseCase,
            logoutUseCase = logoutUseCase
        ) as T
    }
}