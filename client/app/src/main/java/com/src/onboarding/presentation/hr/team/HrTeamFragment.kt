package com.src.onboarding.presentation.hr.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentHrTeamBinding
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.notifications.NotificationsFragment
import com.src.onboarding.presentation.hr.add_employee.AddEmployeeFragment
import com.src.onboarding.presentation.hr.profile.HrEmployeeProfileFragment
import com.src.onboarding.presentation.hr.team.adapter.TeammateAdapter
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModel

class HrTeamFragment : Fragment() {
    private lateinit var binding: FragmentHrTeamBinding
    private lateinit var viewModel: HrTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHrTeamBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getHrTeamViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataColleagueState.observe(
            this.viewLifecycleOwner, this::checkColleaguesState
        )
        viewModel.liveDataGetProfileState.observe(
            this.viewLifecycleOwner, this::checkGetProfileState
        )
        viewModel.liveDataGetCountNotificationsState.observe(
            this.viewLifecycleOwner, this::checkGetCountNotificationState
        )
        setAdapterForNotifications()
        setOnAddButtonClickListener()
        viewModel.getColleagues()
        viewModel.getProfile()
        viewModel.getCountNotifications()
        setOnNotificationClickListener()
    }

    private fun setOnNotificationClickListener() {
        binding.ivNotifications.setOnClickListener {
            (activity as MainActivity).replaceFragment(NotificationsFragment())
        }
    }

    private fun checkColleaguesState(state: ColleagueState<List<Colleague>>) {
        when (state) {
            is ColleagueState.SuccessState -> {
                setDataForRecycler(state.data)

            }
            is ColleagueState.LoadingState -> {

            }
            is ColleagueState.ErrorState -> {

            }
            is ColleagueState.NotWorkingState -> {
            }
        }
    }

    private fun setAdapterForNotifications() {
        binding.rvColleagues.adapter = TeammateAdapter { onCLickColleague(it) }
        binding.rvColleagues.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
    }

    private fun setDataForRecycler(colleague: List<Colleague>) {
        val adapter = binding.rvColleagues.adapter as TeammateAdapter
        adapter.submitList(colleague)
    }

    private fun setOnAddButtonClickListener() {
        binding.cvAddTask.setOnClickListener {
            (activity as HrActivity).replaceFragment(AddEmployeeFragment())
        }
    }

    private fun onCLickColleague(colleague: Colleague) {
        val bundle = Bundle()
        bundle.putLong(HrEmployeeProfileFragment.USER_ID, colleague.id)
        val fragment = HrEmployeeProfileFragment()
        fragment.arguments = bundle
        (activity as HrActivity).replaceFragment(fragment)
    }

    private fun checkGetProfileState(state: BasicState<UserProfile>) {
        when (state) {
            is BasicState.SuccessState -> {
                binding.tvTeamTitle.text = state.data.post!!
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
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
}
