package com.src.onboarding.presentation.courses.courses_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentCoursesMainBinding
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.details.CourseDetailsFragment
import com.src.onboarding.presentation.courses.all_courses.AllCoursesFragment
import com.src.onboarding.presentation.courses.courses_main.adapter.ColleagueAdapter
import com.src.onboarding.presentation.courses.courses_main.adapter.CoursesAdapter
import com.src.onboarding.presentation.courses.courses_main.adapter.MainCourseItemDecoration
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel
import com.src.onboarding.presentation.courses.notifications.NotificationsFragment
import com.src.onboarding.presentation.profile.user_profile.UserProfileFragment

//TODO ПРОВЕРКА ЧТО ЭТО НЕ ТЫ!!!!!
class CoursesMainFragment : Fragment() {
    private lateinit var binding: FragmentCoursesMainBinding
    private lateinit var viewModel: CourseMainViewModel
    private val mainCourseItemDecoration = MainCourseItemDecoration()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesMainBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getCourseViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataColleagueState.observe(
            this.viewLifecycleOwner, this::checkColleaguesState
        )
        viewModel.liveDataCoursesCountLimitState.observe(
            this.viewLifecycleOwner, this::checkCoursesState
        )
        viewModel.liveDataGetCountNotificationsState.observe(
            this.viewLifecycleOwner, this::checkGetCountNotificationState
        )
        viewModel.liveDataGetProfileState.observe(
            this.viewLifecycleOwner, this::checkGetProfileState
        )
        viewModel.getColleagues()
        viewModel.getCourses()
        viewModel.getCountNotifications()
        viewModel.getProfile()
        setAdapters()
        setOnNotificationClickListener()
        setOnAllCoursesButtonClickListener()
        setOnProfilePictureClickListener()
    }

    private fun setOnProfilePictureClickListener() {
        binding.cvUserPicture.setOnClickListener {
            (activity as MainActivity).replaceFragment(UserProfileFragment())
        }
    }

    private fun setOnNotificationClickListener() {
        binding.ivNotifications.setOnClickListener {
            (activity as MainActivity).replaceFragment(NotificationsFragment())
        }
    }

    private fun setOnAllCoursesButtonClickListener() {
        binding.tvAllCourses.setOnClickListener {
            (activity as MainActivity).replaceFragment(AllCoursesFragment())
        }
    }

    private fun setAdapters() {
        setAdapterForColleaguesRecyclerView()
        setAdapterForCoursesRecyclerView()
    }

    private fun checkColleaguesState(state: ColleagueState<List<Colleague>>) {
        when (state) {
            is ColleagueState.SuccessState -> {
                setDataForColleaguesRecyclerView(state.data)

            }
            is ColleagueState.LoadingState -> {

            }
            is ColleagueState.ErrorState -> {
                (activity as MainActivity).showSnackBar(null)

            }
            is ColleagueState.NotWorkingState -> {
                binding.clMainCourseProgressCard.visibility = View.GONE
                binding.tvColleagues.visibility = View.GONE
                binding.rvColleagues.visibility = View.GONE

            }
        }
    }

    private fun setAdapterForColleaguesRecyclerView() {
        val adapter = ColleagueAdapter { onClickColleague(it) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvColleagues.layoutManager = layoutManager
        binding.rvColleagues.adapter = adapter
        binding.rvColleagues.addItemDecoration(mainCourseItemDecoration)
    }

    //TODO
    private fun onClickColleague(colleague: Colleague) {
        val bundle = Bundle()
        bundle.putLong(UserProfileFragment.USER_ID, colleague.id)
        val fragment = UserProfileFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    private fun setDataForColleaguesRecyclerView(colleagues: List<Colleague>) {
        val adapter = binding.rvColleagues.adapter as ColleagueAdapter
        adapter.submitList(colleagues)
    }

    //TODO сообщить об ошибке
    //TODO добавить текущий тест
    private fun checkCoursesState(state: BasicState<MainCourse>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForCoursesRecyclerView(state.data.allCourses)
                setCurrentCourse(state.data.currentCourse)

            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun setAdapterForCoursesRecyclerView() {
        val adapter = CoursesAdapter { onClickCourses(it) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvCourses.layoutManager = layoutManager
        binding.rvCourses.adapter = adapter
        binding.rvCourses.addItemDecoration(mainCourseItemDecoration)
    }

    //TODo добавить плейсхолдер
    private fun setCurrentCourse(course: Course?) {
        if (course == null) {
            binding.clMainCourseProgressCard.visibility = View.GONE
        } else {
            Glide.with(requireContext())
                .load(course.image)
                .into(binding.ivMainCourse)
            binding.tvMainCourse.text = course.name
            val percent = if (course.percentageOfCompletion == null) {
                0
            } else course.percentageOfCompletion.toInt()
            binding.pbProgressBar.progress = percent
        }
    }

    //TODO
    private fun onClickCourses(course: Course) {
        val bundle = Bundle()
        bundle.putLong(CourseDetailsFragment.COURSE_ID, course.id)
        val fragment = CourseDetailsFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    private fun setDataForCoursesRecyclerView(courses: List<Course>) {
        val adapter = binding.rvCourses.adapter as CoursesAdapter
        adapter.submitList(courses)
    }

    private fun checkGetCountNotificationState(state: BasicState<Long>) {
        when (state) {
            is BasicState.SuccessState -> {
                val notificationsCount = state.data
                if (notificationsCount == 0L) {
                    binding.tvNotificationCount.visibility = View.GONE
                } else {
                    val notificationsCountString = if (notificationsCount > 9) {
                        getString(R.string.too_many_notifications)
                    } else {
                        notificationsCount.toString()
                    }
                    binding.tvNotificationCount.visibility = View.VISIBLE
                    binding.tvNotificationCount.text = notificationsCountString
                }
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun checkGetProfileState(state: BasicState<UserProfile>) {
        when (state) {
            is BasicState.SuccessState -> {
                binding.tvUserName.text = state.data.name
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }
}