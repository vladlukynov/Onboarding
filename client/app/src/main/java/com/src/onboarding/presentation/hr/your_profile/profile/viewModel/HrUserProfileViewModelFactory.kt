package com.src.onboarding.presentation.hr.your_profile.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.user.EditProfileUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import com.src.onboarding.presentation.profile.edit_profile.viewModel.UserProfileViewModel
import javax.inject.Inject

class HrUserProfileViewModelFactory @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val editProfileUseCase: EditProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(
            getUserProfileUseCase = getUserProfileUseCase,
            getStartedCoursesForUserUseCase = getStartedCoursesForUserUseCase,
            getActivitiesUseCase = getActivitiesUseCase,
            editProfileUseCase = editProfileUseCase
        ) as T
    }
}