package com.src.onboarding.presentation.profile.user_profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentUserProfileBinding
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.profile.edit_profile.EditProfileFragment
import com.src.onboarding.presentation.profile.user_profile.adapter.StatisticAdapter
import com.src.onboarding.presentation.profile.user_profile.adapter.ActivityAdapter
import com.src.onboarding.presentation.profile.user_profile.viewModel.UserProfileViewModel

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getUserProfileViewModel()
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
        viewModel.getProfile()
        viewModel.getStartedCourses()
        viewModel.getActivities()
        setAdaptersForStatisticRecyclerView()
        setAdaptersForActivitiesRecyclerView()
        setBackButtonOnClick()
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.tvAchievements.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.ivMan.visibility = View.VISIBLE

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
            binding.ivMan.visibility = View.INVISIBLE

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
            binding.ivMan.visibility = View.INVISIBLE

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
            binding.tvAchievements.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_grey
                )
            )
        }
        binding.tvEditProfile.setOnClickListener {
            (activity as MainActivity).replaceFragment(EditProfileFragment())
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
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
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

    //TODO
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

    companion object {
        const val USER_ID = "user_id"
    }

}