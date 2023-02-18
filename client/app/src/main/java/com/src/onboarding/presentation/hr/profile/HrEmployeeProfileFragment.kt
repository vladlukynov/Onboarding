package com.src.onboarding.presentation.hr.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentHrEmployeeProfileBinding
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.model.task.TaskWithTitle
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.courses.tasks.adapter.TasksAdapter
import com.src.onboarding.presentation.hr.add_task.AddTaskFragment
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModel
import com.src.onboarding.presentation.profile.user_profile.adapter.ActivityAdapter
import com.src.onboarding.presentation.profile.user_profile.adapter.StatisticAdapter

//TODO ПРОВЕРКА ЧТО ЭТО НЕ ТЫ!!!!!
class HrEmployeeProfileFragment : Fragment() {
    lateinit var binding: FragmentHrEmployeeProfileBinding
    private lateinit var viewModel: HrEmployeeProfileViewModel
    private var userId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val args = this.arguments
        userId = args?.getLong(USER_ID)!!
        binding = FragmentHrEmployeeProfileBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getHrEmployeeProfileViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveDataGetProfileState.observe(
            this.viewLifecycleOwner, this::checkGetProfileState
        )
        viewModel.liveDataGetStartedCourseState.observe(
            this.viewLifecycleOwner, this::checkGetStartedCourseState
        )
        viewModel.liveDataGetActivitiesState.observe(
            this.viewLifecycleOwner, this::checkGetSActivitiesState
        )
        viewModel.liveDataTasksState.observe(
            this.viewLifecycleOwner, this::checkGetTasksState
        )
        viewModel.getProfile()
        viewModel.getStartedCourses()
        viewModel.getActivities()
        viewModel.getTasks(userId)
        setAdaptersForStatisticRecyclerView()
        setAdaptersForActivitiesRecyclerView()
        setAdapterForTasksRecyclerView()
        setBackButtonOnClick()
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.tvAchievements.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.rvTasks.visibility = View.INVISIBLE
            binding.cvAddTask.visibility=View.INVISIBLE

            binding.tvActivity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvStatistics.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvTasks.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvAchievements.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_purple
                )
            )
        }

        binding.tvActivity.setOnClickListener {
            binding.rvActivity.visibility = View.VISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.rvTasks.visibility = View.INVISIBLE
            binding.cvAddTask.visibility=View.INVISIBLE

            binding.tvActivity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_purple
                )
            )
            binding.tvStatistics.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvTasks.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvAchievements.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
        }

        binding.tvStatistics.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.INVISIBLE
            binding.cvAddTask.visibility=View.INVISIBLE

            binding.tvActivity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvStatistics.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_purple
                )
            )
            binding.tvTasks.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvAchievements.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
        }
        binding.tvTasks.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.rvTasks.visibility = View.VISIBLE
            binding.cvAddTask.visibility=View.VISIBLE

            binding.tvActivity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvStatistics.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
            binding.tvTasks.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_purple
                )
            )
            binding.tvAchievements.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
        }
        binding.cvAddTask.setOnClickListener {
            (activity as HrActivity).replaceFragment(AddTaskFragment())
        }
    }

    private fun checkGetProfileState(state: BasicState<UserProfile>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForProfile(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun checkGetStartedCourseState(state: BasicState<List<Course>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForStatistics(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataForProfile(userProfile: UserProfile) {
        Glide.with(requireContext())
            .load(userProfile.image)
            .into(binding.ivUserPicture)
        if (userProfile.description == null || userProfile.description == "") {
            binding.tvAbout.visibility = View.GONE
        } else {
            binding.tvAbout.text = getString(R.string.about_me) + " " + userProfile.description
        }
        binding.tvUserName.text = userProfile.name
        binding.tvEmail.text = userProfile.email
        if (userProfile.post == null || userProfile.post == "") {
            binding.tvUserPosition.visibility = View.GONE
        } else {
            binding.tvUserPosition.text = userProfile.post
        }

    }

    private fun setAdaptersForStatisticRecyclerView() {
        val adapter = StatisticAdapter { onClickCourses(it) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvStatistics.layoutManager = layoutManager
        binding.rvStatistics.adapter = adapter
    }

    //TODO не надо исправлять!!! оставляейте пустым!!!
    private fun onClickCourses(course: Course) {

    }

    private fun setDataForStatistics(courses: List<Course>) {
        val adapter = binding.rvStatistics.adapter as StatisticAdapter
        adapter.submitList(courses)
    }

    private fun setBackButtonOnClick() {
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun checkGetSActivitiesState(state: BasicState<List<Activity>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForActivities(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun setAdaptersForActivitiesRecyclerView() {
        val adapter = ActivityAdapter()
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvActivity.layoutManager = layoutManager
        binding.rvActivity.adapter = adapter
    }

    private fun setDataForActivities(activities: List<Activity>) {
        val adapter = binding.rvActivity.adapter as ActivityAdapter
        adapter.submitList(activities)
    }

    private fun setAdapterForTasksRecyclerView() {
        val adapter = TasksAdapter { onClickTasksComplete(it) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutManager
        binding.rvTasks.adapter = adapter
    }

    //TODO не надо исправлять!!! оставляейте пустым!!!
    private fun onClickTasksComplete(task: TaskWithTitle) {

    }

    private fun setDataDorTasks(tasks: List<Task>) {
        val adapter = binding.rvTasks.adapter as TasksAdapter
        adapter.submitList(tasks.map { TaskWithTitle.convertTaskModelToTaskWithTitleTaskModel(it) })
    }

    private fun checkGetTasksState(state: BasicState<List<Task>>) {
        when (state) {
            is BasicState.SuccessState -> {
                Log.d("Checktasks","success ${state.data.size}")
                setDataDorTasks(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    companion object {
        const val USER_ID = "user_id"
    }
}
