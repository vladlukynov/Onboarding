package com.src.onboarding.presentation.welcome.recovery.recovery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.login.RecoveryPasswordUseCase
import javax.inject.Inject

class PasswordRecoveryViewModelFactory @Inject constructor(private val recoveryPasswordUseCase: RecoveryPasswordUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordRecoveryViewModel(changePasswordUseCase = recoveryPasswordUseCase) as T
    }
}