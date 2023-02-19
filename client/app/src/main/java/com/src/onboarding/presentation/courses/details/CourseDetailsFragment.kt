package com.src.onboarding.presentation.courses.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.FragmentCourseDetailsBinding
import com.src.onboarding.domain.model.course_page.BlockItemWithTitle
import com.src.onboarding.domain.model.course_page.CourseBlock
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.all_courses.GlobalItemDecoration
import com.src.onboarding.presentation.courses.details.adapter.CoursePageAdapter
import com.src.onboarding.presentation.courses.details.adapter.CoursePageItemDecoration
import com.src.onboarding.presentation.courses.details.viewModel.CourseDetailsViewModel

class CourseDetailsFragment : Fragment() {
    lateinit var binding: FragmentCourseDetailsBinding
    lateinit var viewModel: CourseDetailsViewModel
    private var courseId: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = this.arguments
        courseId = args?.getLong(COURSE_ID)!!
        binding = FragmentCourseDetailsBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getCourseDetailsViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataCoursesState.observe(
            this.viewLifecycleOwner, this::checkCoursesState
        )
        viewModel.getCourses(courseId = courseId)
        setAdapterForRecyclerView()
    }

    private fun checkCoursesState(state: BasicState<CourseBlock>) {
        when (state) {
            is BasicState.SuccessState -> {
                setData(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(course: CourseBlock) {
        binding.tvCourseTitle.text = course.name
        binding.tvCourseDescription.text = course.description
        binding.tvTopicsNumber.text = course.countThemes.toString() + " тем"
        val blockWithTitle = ArrayList<BlockItemWithTitle>(emptyList())
        for (block in course.blocks) {
            blockWithTitle.add(BlockItemWithTitle.TitleModel(block.name))
            for (blockItem in block.itemInBlock) {
                blockWithTitle.add(
                    BlockItemWithTitle.convertBlockModelToBlockWithTitleTaskModel(
                        blockItem
                    )
                )
            }
        }
        val adapter = binding.rvCourseTopics.adapter as CoursePageAdapter
        adapter.submitList(blockWithTitle)
    }

    private fun setAdapterForRecyclerView() {
        val adapter = CoursePageAdapter { item -> onClickBlock(block = item) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvCourseTopics.layoutManager = layoutManager
        binding.rvCourseTopics.adapter = adapter
        binding.rvCourseTopics.addItemDecoration(
            CoursePageItemDecoration()
        )
    }

    private fun onClickBlock(block: BlockItemWithTitle.BlockItemModel) {

    }

    companion object {
        const val COURSE_ID = "course_id"
    }
}
