package com.src.onboarding.presentation.courses.courses_main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import kotlinx.coroutines.launch

class CourseMainViewModel(private val getColleaguesUseCase: GetColleaguesUseCase) : ViewModel() {
    private val _mutableLiveDataColleagueState =
        MutableLiveData<ColleagueState<List<Colleague>>>(ColleagueState.LoadingState())

    val liveDataColleagueState get() = _mutableLiveDataColleagueState

    fun getColleagues() {
        viewModelScope.launch {
            _mutableLiveDataColleagueState.value = getColleaguesUseCase.execute()
        }
    }
}