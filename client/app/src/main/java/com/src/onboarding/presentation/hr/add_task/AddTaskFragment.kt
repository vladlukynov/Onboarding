package com.src.onboarding.presentation.hr.add_task;

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentAddTaskBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.hr.add_task.viewModel.AddTaskViewModel
import com.src.onboarding.presentation.hr.profile.HrEmployeeProfileFragment

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel
    private var userId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val args = this.arguments
        userId = args?.getLong(HrEmployeeProfileFragment.USER_ID)!!
        binding = FragmentAddTaskBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getAddViewModel()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackButtonClickListener()
        setOnDeadlineDateClickListener()
        viewModel.liveDataAddTaskState.observe(
            this.viewLifecycleOwner, this::checkAddWorkerState
        )
        binding.tvSaveButton.setOnClickListener {
            val header = binding.etEmail.text.toString()
            val deadline = binding.tvDeadlineDate.text.toString()
            viewModel.addTask(userId = userId, header = header, deadline = deadline)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setOnDeadlineDateClickListener() {
        binding.tvDeadlineDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { datePicker, i, i2, i3 ->
                val str = "$i3/${i2 + 1}/$i"
                binding.tvDeadlineDate.text = str
            }
            datePickerDialog.show()
        }
    }

    private fun setOnBackButtonClickListener() {
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }

    private fun checkAddWorkerState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
                parentFragmentManager.popBackStack()
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {}
        }
    }

    companion object {
        const val USER_ID = "user_id"
    }
}
