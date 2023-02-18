package com.src.onboarding.presentation.hr.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.FragmentHrTeamBinding
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.hr.add_task.AddTaskFragment
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
        setAdapterForNotifications()
        setOnAddButtonClickListener()
        viewModel.getColleagues()
        viewModel.getProfile()
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
            (activity as HrActivity).replaceFragment(AddTaskFragment())
        }
    }

    //TODO перейти на страницу коллеги
    private fun onCLickColleague(colleague: Colleague) {

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
}
