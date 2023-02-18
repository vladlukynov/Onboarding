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

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackButtonClickListener()
        setOnDeadlineDateClickListener()
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
}
