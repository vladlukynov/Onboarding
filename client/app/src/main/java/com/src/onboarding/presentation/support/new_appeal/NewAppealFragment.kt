package com.src.onboarding.presentation.support.new_appeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentNewAppealBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModel

class NewAppealFragment : Fragment() {
    private lateinit var binding: FragmentNewAppealBinding
    private lateinit var viewModel: NewAppealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAppealBinding.inflate(inflater)
        if(activity is MainActivity) {
            viewModel = (activity as MainActivity).getNewAppealViewModel()
        } else {
            viewModel = (activity as HrActivity).getNewAppealViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveDataAddQuestionState.observe(
            this.viewLifecycleOwner, this::checkQuestionState
        )
        viewModel.liveDataAddAnswerState.observe(
            this.viewLifecycleOwner, this::checkAnswerState
        )

        setOnSendMessageButtonClickListener()
    }

    private fun checkQuestionState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
                binding.cvSendMessageButton.visibility = View.GONE
                binding.tilMessage.visibility = View.GONE
                binding.clClientMessage.visibility = View.VISIBLE
            }
            is BasicState.ErrorState -> {

            }
            is BasicState.LoadingState -> {

            }
        }
    }

    private fun checkAnswerState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {//?????? ?????????? ?????????????? ???????????????? (?????? ?????? ???????????? ???????????????????? ???????? ????????)
            }
            is BasicState.ErrorState -> {

            }
            is BasicState.LoadingState -> {

            }
        }
    }

    private fun setOnSendMessageButtonClickListener() {
        binding.ivSendMessageButton.setOnClickListener {
            val question = binding.etMessage.text
            binding.tvAppealClientMessage.text = binding.etMessage.text
            viewModel.addQuestion(question.toString())
        }
    }
}
