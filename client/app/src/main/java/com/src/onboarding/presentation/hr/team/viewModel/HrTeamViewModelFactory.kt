package com.src.onboarding.presentation.hr.team.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import com.src.onboarding.domain.usecase.user.GetCountNotificationUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import javax.inject.Inject

class HrTeamViewModelFactory @Inject constructor(
    private val getColleaguesUseCase: GetColleaguesUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCountNotificationUseCase: GetCountNotificationUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HrTeamViewModel(
            getColleaguesUseCase = getColleaguesUseCase,
            getUserProfileUseCase = getUserProfileUseCase,
            getCountNotificationsUseCase = getCountNotificationUseCase
        ) as T
    }
}