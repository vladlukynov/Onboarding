package com.src.onboarding.presentation.hr.add_employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentAddEmployeeBinding
import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.hr.add_employee.adapter.AddEmployeeSpinnerAdapter
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModel
import com.src.onboarding.presentation.hr.team.HrTeamFragment

//TODO добавить загрузку
class AddEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentAddEmployeeBinding
    private lateinit var viewModel: AddEmployeeViewModel
    private var teams: List<Team> = emptyList()
    private var posts: List<Post> = emptyList()
    private var postIsLoaded = false
    private var teamIsLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEmployeeBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getAddEmployeeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataPostState.observe(
            this.viewLifecycleOwner, this::checkPostState
        )
        viewModel.liveDataTeamState.observe(
            this.viewLifecycleOwner, this::checkTeamState
        )
        viewModel.liveDataAddWorkerState.observe(
            this.viewLifecycleOwner, this::checkAddWorkerState
        )
        viewModel.getPosts()
        viewModel.getTeams()
        setOnClickListenerForContinueButton()
        setOnBackButtonClickListener()
    }

    private fun checkPostState(state: BasicState<List<Post>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setPostsForSpinner(state.data)
                posts = state.data
            }
            is BasicState.LoadingState -> {
                postIsLoaded = true
            }
            is BasicState.ErrorState -> {}
        }

    }

    private fun setPostsForSpinner(posts: List<Post>) {
        val postString: MutableList<String> = posts.map { it.name }.toMutableList()
        binding.sPost.adapter = AddEmployeeSpinnerAdapter(postString)
    }

    private fun checkTeamState(state: BasicState<List<Team>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setTeamsForSpinner(state.data)
                teams = state.data
            }
            is BasicState.LoadingState -> {
                teamIsLoaded = true
            }
            is BasicState.ErrorState -> {}
        }
    }

    private fun setTeamsForSpinner(teams: List<Team>) {
        val teamString: MutableList<String> = teams.map { it.name }.toMutableList()
        binding.sTeam.adapter = AddEmployeeSpinnerAdapter(teamString)
    }

    private fun checkAddWorkerState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
                //TODO перейти на другой
                parentFragmentManager.popBackStack()
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {}
        }
    }

    //TODO добавить обработку ошибок
    //TODO gроверить почту (что это ваще почта, такая почта существует)
    private fun setOnClickListenerForContinueButton() {
        binding.tvSaveButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val teamString = binding.sTeam.selectedItem.toString()
            var teamId: Long = 0
            for (team in teams) {
                if (team.name == teamString) {
                    teamId = team.id
                    break
                }
            }
            val postString = binding.sPost.selectedItem.toString()
            var postId: Long = 0
            for (post in posts) {
                if (post.name == postString) {
                    postId = post.id
                    break
                }
            }
            viewModel.addWorker(email = email, postId = postId, teamId = teamId)
        }
    }

    private fun setOnBackButtonClickListener() {
        binding.ibBackButton.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }
}
