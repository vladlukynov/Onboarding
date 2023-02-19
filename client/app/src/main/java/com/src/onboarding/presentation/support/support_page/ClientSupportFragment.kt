package com.src.onboarding.presentation.support.support_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.src.onboarding.databinding.FragmentClientSupportBinding
import com.src.onboarding.domain.model.user.Question
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.support.new_appeal.NewAppealFragment
import com.src.onboarding.presentation.support.support_page.adapter.HrAppealAdapter
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModel

class ClientSupportFragment : Fragment() {
    private lateinit var binding: FragmentClientSupportBinding
    private lateinit var viewModel: ClientSupportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientSupportBinding.inflate(inflater)
        if (activity is HrActivity)
            binding.cvAddAppeal.visibility = View.GONE
        viewModel = (activity as HrActivity).getClientSupportViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnAddAppealButtonClick()
        setRecyclerAdapter()
        viewModel.liveDataGetQuestionState.observe(
            this.viewLifecycleOwner, this::checkQuestionsState
        )

        viewModel.getQuestions(false)
    }

    private fun setOnAddAppealButtonClick() {
        binding.cvAddAppeal.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewAppealFragment())
        }
    }

    private fun setRecyclerAdapter() {
        binding.rvAppeals.adapter = HrAppealAdapter()
        binding.rvAppeals.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setDataForRecycler(list: List<Question>) {
        val adapter = binding.rvAppeals.adapter as HrAppealAdapter
        adapter.submitList(list)
    }

    private fun checkQuestionsState(state: BasicState<List<Question>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForRecycler(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }
}
