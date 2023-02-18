package com.src.onboarding.presentation.courses.notifications.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.user.ClearNotificationsUseCase
import com.src.onboarding.domain.usecase.user.GetNotificationsUseCase
import javax.inject.Inject

class NotificationViewModelFactory @Inject constructor(
    private val clearNotificationsUseCase: ClearNotificationsUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(
            clearNotificationsUseCase = clearNotificationsUseCase,
            getNotificationsUseCase = getNotificationsUseCase
        ) as T
    }
}