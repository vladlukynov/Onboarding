package com.src.onboarding.presentation.welcome.sign_in.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.login.GetPostIdUseCase
import com.src.onboarding.domain.usecase.login.SignInUseCase
import javax.inject.Inject

class SignInViewModelFactory @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getPostIdUseCase: GetPostIdUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(
            signInUseCase = signInUseCase,
            getPostIdUseCase = getPostIdUseCase
        ) as T
    }
}