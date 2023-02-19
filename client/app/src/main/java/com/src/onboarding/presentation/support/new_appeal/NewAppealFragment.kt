package com.src.onboarding.presentation.support.new_appeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentNewAppealBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModel

class NewAppealFragment : Fragment() {
    private lateinit var binding: FragmentNewAppealBinding
    private lateinit var viewModel: NewAppealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAppealBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getNewAppealViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveDataAddQuestionState.observe(
            this.viewLifecycleOwner, this::checkQuestionState
        )
        viewModel.liveDataAddAnswerState.observe(
            this.viewLifecycleOwner, this::checkAnswerState
        )
        //  viewModel.addQuestion() - вот с помощью этой штуки добавяем вопрос
        //viewModel.addAnswer() - а это ответ
        //туда нужно передать параметры, посмотрите во вью модели
    }

    private fun checkQuestionState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {//ура вопрос успешно добавлен  (вот тут можете перхеодить хоть куда)
            }
            is BasicState.ErrorState -> {

            }
            is BasicState.LoadingState -> {

            }
        }
    }

    private fun checkAnswerState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {//ура ответ успешно добавлен (вот тут можете перхеодить хоть куда)
            }
            is BasicState.ErrorState -> {

            }
            is BasicState.LoadingState -> {

            }
        }
    }
}
