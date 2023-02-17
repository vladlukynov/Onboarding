package com.src.onboarding.presentation.welcome.recovery.recovery_email.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.login.SendCodeForRecoveryPasswordUseCase
import javax.inject.Inject

class PasswordRecoveryEnterEmailViewModelFactory @Inject constructor(
    private val sendCodeForRecoveryPasswordUseCase: SendCodeForRecoveryPasswordUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordRecoveryEnterEmailViewModel(sendCodeForRecoveryPasswordUseCase = sendCodeForRecoveryPasswordUseCase) as T
    }
}