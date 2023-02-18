package com.src.onboarding.presentation.courses.courses_main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import com.src.onboarding.domain.usecase.course.GetCoursesUseCase
import kotlinx.coroutines.launch

class CourseMainViewModel(
    private val getColleaguesUseCase: GetColleaguesUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {
    private val _mutableLiveDataColleagueState =
        MutableLiveData<ColleagueState<List<Colleague>>>(ColleagueState.LoadingState())
    private val _mutableLiveDataCoursesState =
        MutableLiveData<BasicState<MainCourse>>(BasicState.LoadingState())

    val liveDataColleagueState get() = _mutableLiveDataColleagueState
    val liveDataCoursesState get() = _mutableLiveDataCoursesState
    fun getColleagues() {
        viewModelScope.launch {
            _mutableLiveDataColleagueState.value = ColleagueState.LoadingState()
            _mutableLiveDataColleagueState.value = getColleaguesUseCase.execute()
        }
    }

    fun getCourses() {
        viewModelScope.launch {
            _mutableLiveDataCoursesState.value = BasicState.LoadingState()
            _mutableLiveDataCoursesState.value = getCoursesUseCase.execute()
        }
    }
}