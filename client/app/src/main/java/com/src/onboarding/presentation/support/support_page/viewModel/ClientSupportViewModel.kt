package com.src.onboarding.presentation.support.support_page.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.user.Question
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.user.GetQuestionsUseCase
import kotlinx.coroutines.launch

//TODO получить все
class ClientSupportViewModel(private val getQuestionsUseCase: GetQuestionsUseCase) : ViewModel() {
    private val _mutableLiveDataGetQuestionsState =
        MutableLiveData<BasicState<List<Question>>>(BasicState.LoadingState())
    val liveDataGetQuestionState get() = _mutableLiveDataGetQuestionsState
    fun getQuestions(isCompleted: Boolean) {
        viewModelScope.launch {
            _mutableLiveDataGetQuestionsState.value = BasicState.LoadingState()
            _mutableLiveDataGetQuestionsState.value = getQuestionsUseCase.execute(isCompleted)
        }
    }

}