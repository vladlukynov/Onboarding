package com.src.onboarding.presentation.courses.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course_page.CourseBlock
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.course.GetCoursesForCoursePageUseCase
import kotlinx.coroutines.launch

class CourseDetailsViewModel(private val getCoursesForCoursePageUseCase: GetCoursesForCoursePageUseCase) :
    ViewModel() {
    private val _mutableLiveDataCoursesState =
        MutableLiveData<BasicState<CourseBlock>>(BasicState.LoadingState())
    val liveDataCoursesState get() = _mutableLiveDataCoursesState
    fun getCourses(courseId: Long) {
        viewModelScope.launch {
            _mutableLiveDataCoursesState.value = BasicState.LoadingState()
            _mutableLiveDataCoursesState.value = getCoursesForCoursePageUseCase.execute(courseId)
        }
    }
}