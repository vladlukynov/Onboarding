package com.src.onboarding.presentation.welcome.recovery.recovery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.login.GetPostIdUseCase
import com.src.onboarding.domain.usecase.login.RecoverPasswordUseCase
import javax.inject.Inject

class PasswordRecoveryViewModelFactory @Inject constructor(
    private val recoveryPasswordUseCase: RecoverPasswordUseCase,
    private val getPostIdUseCase: GetPostIdUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordRecoveryViewModel(
            changePasswordUseCase = recoveryPasswordUseCase,
            getPostIdUseCase = getPostIdUseCase
        ) as T
    }
}