package com.src.onboarding.presentation.courses.all_courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentAllCoursesBinding
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.CourseDetailsFragment

class AllCoursesFragment : Fragment() {
    lateinit var binding: FragmentAllCoursesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCoursesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonOnClick()
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
}