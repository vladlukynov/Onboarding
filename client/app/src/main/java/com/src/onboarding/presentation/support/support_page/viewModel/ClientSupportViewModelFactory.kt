package com.src.onboarding.presentation.support.support_page.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.user.GetQuestionsUseCase
import javax.inject.Inject

class ClientSupportViewModelFactory @Inject constructor(private val getQuestionsUseCase: GetQuestionsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClientSupportViewModel(
            getQuestionsUseCase = getQuestionsUseCase
        ) as T
    }
}