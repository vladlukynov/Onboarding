package com.src.onboarding.presentation.profile.user_profile.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.user.EditProfileState
import com.src.onboarding.domain.usecase.course.GetStartedCoursesByIdForUserUseCase
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.user.*
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getStartedCoursesByIdForUserUseCase: GetStartedCoursesByIdForUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    private val _mutableLiveDataGetProfileState =
        MutableLiveData<BasicState<UserProfile>>(BasicState.LoadingState())
    private val _mutableLiveDataEditProfileState =
        MutableLiveData<EditProfileState>(EditProfileState.DefaultState)
    private val _mutableLiveDataGetStartedCoursesForUserState =
        MutableLiveData<BasicState<List<Course>>>(BasicState.LoadingState())
    private val _mutableLiveDataGetStartedByIdCoursesForUserState =
        MutableLiveData<BasicState<List<Course>>>(BasicState.LoadingState())
    private val _mutableLiveDataGetUserByIdState =
        MutableLiveData<BasicState<UserProfile>>(BasicState.LoadingState())
    private val _mutableLiveDataGetActivitiesState =
        MutableLiveData<BasicState<List<Activity>>>(BasicState.LoadingState())
    private val _mutableLiveDataLogoutState =
        MutableLiveData<BasicState<Unit>>(BasicState.DefaultState())

    private val _mutableLiveDataName = MutableLiveData<String?>(null)
    private val _mutableLiveDataDescription = MutableLiveData<String?>(null)
    private val _mutableLiveDataPhotoUri = MutableLiveData<Uri?>(null)
    private val _mutableLiveDataPhotoString = MutableLiveData<String?>(null)

    val liveDataGetProfileState get() = _mutableLiveDataGetProfileState
    val liveDataGetUserByIdState get() = _mutableLiveDataGetUserByIdState
    val liveDataGetStartedCourseState get() = _mutableLiveDataGetStartedCoursesForUserState
    val liveDataGetStartedCourseByIdState get() = _mutableLiveDataGetStartedByIdCoursesForUserState
    val liveDataGetActivitiesState get() = _mutableLiveDataGetActivitiesState
    val liveDataEditProfileState get() = _mutableLiveDataEditProfileState
    val liveDataLogoutState get() = _mutableLiveDataLogoutState
    fun getProfile() {
        viewModelScope.launch {
            _mutableLiveDataGetProfileState.value = BasicState.LoadingState()
            val state = getUserProfileUseCase.execute()
            _mutableLiveDataGetProfileState.value = state
            if (state is BasicState.SuccessState) {
                val profile = state.data
                _mutableLiveDataName.value = profile.name
                _mutableLiveDataDescription.value = profile.description
                _mutableLiveDataPhotoString.value = profile.image
            }
        }
    }

    fun getUserById(id: Long) {
        viewModelScope.launch {
            _mutableLiveDataGetUserByIdState.value = BasicState.LoadingState()
            val state = getUserByIdUseCase.execute(id = id)
            _mutableLiveDataGetUserByIdState.value = state
            if (state is BasicState.SuccessState) {
                val profile = state.data
                _mutableLiveDataName.value = profile.name
                _mutableLiveDataDescription.value = profile.description
                _mutableLiveDataPhotoString.value = profile.image
            }
        }
    }

    fun getStartedCourses() {
        viewModelScope.launch {
            _mutableLiveDataGetStartedCoursesForUserState.value = BasicState.LoadingState()
            _mutableLiveDataGetStartedCoursesForUserState.value =
                getStartedCoursesForUserUseCase.execute()
        }
    }

    fun getStartedCoursesById(id: Long) {
        viewModelScope.launch {
            _mutableLiveDataGetStartedByIdCoursesForUserState.value = BasicState.LoadingState()
            _mutableLiveDataGetStartedByIdCoursesForUserState.value =
                getStartedCoursesByIdForUserUseCase.execute(id = id)
        }
    }

    fun getActivities() {
        viewModelScope.launch {
            _mutableLiveDataGetActivitiesState.value = BasicState.LoadingState()
            _mutableLiveDataGetActivitiesState.value = getActivitiesUseCase.execute()
        }
    }

    fun editProfile() {
        viewModelScope.launch {
            _mutableLiveDataEditProfileState.value = EditProfileState.LoadingState
            if (_mutableLiveDataDescription.value != null && _mutableLiveDataName.value != null) {
                _mutableLiveDataEditProfileState.value =
                    editProfileUseCase.execute(
                        description = _mutableLiveDataDescription.value!!,
                        name = _mutableLiveDataName.value!!,
                        uri = _mutableLiveDataPhotoUri.value
                    )
            } else {
                _mutableLiveDataEditProfileState.value = EditProfileState.ErrorState
            }
        }
    }

    fun setName(name: String) {
        _mutableLiveDataName.value = name
    }

    fun setDescription(login: String) {
        _mutableLiveDataDescription.value = login
    }

    fun setPhoto(uri: Uri?) {
        _mutableLiveDataPhotoUri.value = uri
    }

    fun getName(): String? {
        return _mutableLiveDataName.value
    }

    fun getDescription(): String? {
        return _mutableLiveDataDescription.value
    }

    fun getImageString(): String? {
        return _mutableLiveDataPhotoString.value
    }

    fun setEditProfileState() {
        _mutableLiveDataEditProfileState.value = EditProfileState.DefaultState
    }

    fun logout() {
        viewModelScope.launch {
            _mutableLiveDataLogoutState.value = BasicState.LoadingState()
            _mutableLiveDataLogoutState.value = logoutUseCase.execute()
        }
    }
}