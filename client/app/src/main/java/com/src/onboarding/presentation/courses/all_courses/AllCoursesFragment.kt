package com.src.onboarding.presentation.courses.all_courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.FragmentAllCoursesBinding
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.details.CourseDetailsFragment
import com.src.onboarding.presentation.courses.courses_main.adapter.CoursesAdapter
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel


class AllCoursesFragment : Fragment() {
    lateinit var binding: FragmentAllCoursesBinding
    private lateinit var viewModel: CourseMainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCoursesBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getCourseViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonOnClick()
        viewModel.liveDataAllCoursesState.observe(
            this.viewLifecycleOwner, this::checkCoursesState
        )
        setAdapterForCoursesRecyclerView()
    }

    private fun setBackButtonOnClick() {
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun onClickCourses(course: Course) {
        val bundle = Bundle()
        bundle.putLong(CourseDetailsFragment.COURSE_ID, course.id)
        val fragment = AllCoursesFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    private fun checkCoursesState(state: BasicState<MainCourse>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForCoursesRecyclerView(state.data.allCourses)

            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun setAdapterForCoursesRecyclerView() {
        val adapter = CoursesAdapter { onClickCourses(it) }
        val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rvCourses.layoutManager = layoutManager
        binding.rvCourses.adapter = adapter
        binding.rvCourses.addItemDecoration(
            GlobalItemDecoration(
                spanCount = 2,
                includeEdge = true,
                spacing = 120
            )
        )
    }

    private fun setDataForCoursesRecyclerView(courses: List<Course>) {
        val adapter = binding.rvCourses.adapter as CoursesAdapter
        adapter.submitList(courses)
    }
}