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
import com.src.onboarding.domain.usecase.user.GetCountNotificationUseCase
import kotlinx.coroutines.launch

class CourseMainViewModel(
    private val getColleaguesUseCase: GetColleaguesUseCase,
    private val getCoursesUseCase: GetCoursesUseCase,
    private val getCountNotificationsUseCase: GetCountNotificationUseCase
) : ViewModel() {
    private val _mutableLiveDataColleagueState =
        MutableLiveData<ColleagueState<List<Colleague>>>(ColleagueState.LoadingState())
    private val _mutableLiveDataAllCoursesState =
        MutableLiveData<BasicState<MainCourse>>(BasicState.LoadingState())
    private val _mutableLiveDataCoursesCountLimitState =
        MutableLiveData<BasicState<MainCourse>>(BasicState.LoadingState())
    private val _mutableLiveDataGetCountNotificationsState =
        MutableLiveData<BasicState<Long>>(BasicState.LoadingState())

    val liveDataColleagueState get() = _mutableLiveDataColleagueState
    val liveDataAllCoursesState get() = _mutableLiveDataAllCoursesState
    val liveDataCoursesCountLimitState get() = _mutableLiveDataCoursesCountLimitState
    val liveDataGetCountNotificationsState get() = _mutableLiveDataGetCountNotificationsState

    fun getColleagues() {
        viewModelScope.launch {
            _mutableLiveDataColleagueState.value = ColleagueState.LoadingState()
            _mutableLiveDataColleagueState.value = getColleaguesUseCase.execute()
        }
    }

    fun getCourses() {
        viewModelScope.launch {
            _mutableLiveDataAllCoursesState.value = BasicState.LoadingState()
            _mutableLiveDataCoursesCountLimitState.value = BasicState.LoadingState()
            val mainCourses = getCoursesUseCase.execute()
            _mutableLiveDataAllCoursesState.value = mainCourses
            if (mainCourses is BasicState.SuccessState) {
                val allCourse = mainCourses.data.allCourses
                val border = if (allCourse.size < 10) {
                    allCourse.size
                } else {
                    10
                }
                _mutableLiveDataCoursesCountLimitState.value = BasicState.SuccessState(
                    MainCourse(
                        currentCourse = mainCourses.data.currentCourse,
                        allCourses = (0 until border).map { mainCourses.data.allCourses.get(it) }
                            .toList()
                    )
                )
            } else {
                _mutableLiveDataCoursesCountLimitState.value = BasicState.ErrorState()
            }
        }
    }

    fun getCountNotifications() {
        viewModelScope.launch {
            _mutableLiveDataGetCountNotificationsState.value = BasicState.LoadingState()
            _mutableLiveDataGetCountNotificationsState.value =
                getCountNotificationsUseCase.execute()
        }
    }
}