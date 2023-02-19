package com.src.onboarding.presentation.support.new_appeal.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.user.Question
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.user.AddNewAnswerUseCase
import com.src.onboarding.domain.usecase.user.AddNewQuestionUseCase
import kotlinx.coroutines.launch

//добавить, ответить
class NewAppealViewModel(
    private val addNewQuestionUseCase: AddNewQuestionUseCase,
    private val addNewAnswerUseCase: AddNewAnswerUseCase
) : ViewModel() {
    private val _mutableLiveDataAddQuestionState =
        MutableLiveData<BasicState<Unit>>(BasicState.LoadingState())
    private val _mutableLiveDataAddAnswerState =
        MutableLiveData<BasicState<Unit>>(BasicState.LoadingState())
    val liveDataAddQuestionState get() = _mutableLiveDataAddQuestionState
    val liveDataAddAnswerState get() = _mutableLiveDataAddAnswerState

    fun addQuestion(text: String) {
        viewModelScope.launch {
            _mutableLiveDataAddQuestionState.value = BasicState.LoadingState()
            _mutableLiveDataAddQuestionState.value = addNewQuestionUseCase.execute(text)
        }
    }

    fun addAnswer(questionId: Long, text: String) {
        viewModelScope.launch {
            _mutableLiveDataAddAnswerState.value = BasicState.LoadingState()
            _mutableLiveDataAddQuestionState.value = addNewAnswerUseCase.execute(questionId, text)
        }
    }
}