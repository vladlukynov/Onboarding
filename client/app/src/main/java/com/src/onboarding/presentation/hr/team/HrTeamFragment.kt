package com.src.onboarding.presentation.hr.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentHrTeamBinding
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.hr.add_task.AddTaskFragment

class HrTeamFragment : Fragment() {
    lateinit var binding: FragmentHrTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHrTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnAddButtonClickListener()
    }

    private fun setOnAddButtonClickListener() {
        binding.cvAddTask.setOnClickListener {
            (activity as HrActivity).replaceFragment(AddTaskFragment())
        }
    }
}
