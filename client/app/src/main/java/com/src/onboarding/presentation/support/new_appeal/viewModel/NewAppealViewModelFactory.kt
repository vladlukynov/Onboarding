package com.src.onboarding.presentation.support.new_appeal.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.user.AddNewAnswerUseCase
import com.src.onboarding.domain.usecase.user.AddNewQuestionUseCase
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModel
import javax.inject.Inject

class NewAppealViewModelFactory @Inject constructor(
    private val addNewQuestionUseCase: AddNewQuestionUseCase,
    private val addNewAnswerUseCase: AddNewAnswerUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewAppealViewModel(
            addNewQuestionUseCase = addNewQuestionUseCase,
            addNewAnswerUseCase = addNewAnswerUseCase
        ) as T
    }
}