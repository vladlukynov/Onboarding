package com.src.onboarding.presentation.welcome.recovery.recovery_code.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.login.CheckRecoveryCodeUseCase
import com.src.onboarding.domain.usecase.login.SendCodeForRecoveryPasswordUseCase
import javax.inject.Inject

class PasswordRecoveryEnterCodeViewModelFactory @Inject constructor(
    private val checkRecoveryCodeUseCase: CheckRecoveryCodeUseCase,
    private val sendCodeForRecoveryPasswordUseCase: SendCodeForRecoveryPasswordUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordRecoveryEnterCodeViewModel(
            checkRecoveryCodeUseCase = checkRecoveryCodeUseCase,
            sendCodeForRecoveryPasswordUseCase = sendCodeForRecoveryPasswordUseCase
        ) as T
    }
}