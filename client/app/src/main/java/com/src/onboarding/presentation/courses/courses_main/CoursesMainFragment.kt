package com.src.onboarding.presentation.courses.courses_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.FragmentCoursesMainBinding
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.courses_main.adapter.ColleagueAdapter
import com.src.onboarding.presentation.courses.courses_main.adapter.CoursesAdapter
import com.src.onboarding.presentation.courses.courses_main.adapter.MainCourseItemDecoration
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel

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
        viewModel.liveDataCoursesState.observe(
            this.viewLifecycleOwner, this::checkCoursesState
        )
        viewModel.liveDataGetCountNotificationsState.observe(
            this.viewLifecycleOwner, this::checkGetCountNotificationState
        )
        viewModel.getColleagues()
        viewModel.getCourses()
        viewModel.getCountNotifications()
        setAdapters()
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

    }

    private fun setDataForCoursesRecyclerView(courses: List<Course>) {
        val adapter = binding.rvCourses.adapter as CoursesAdapter
        adapter.submitList(courses)
    }

    //TODO проверить количество если больше 9 то ставим 9+
    private fun checkGetCountNotificationState(state: BasicState<Long>) {
        when (state) {
            is BasicState.SuccessState -> {
                binding.tvNotificationCount.text = state.data.toString()
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }

    }
}