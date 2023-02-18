package com.src.onboarding.presentation.welcome.courses.add_employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentAddEmployeeBinding
import com.src.onboarding.presentation.welcome.courses.add_employee.adapter.AddEmployeeSpinnerAdapter

class AddEmployeeFragment : Fragment() {
    lateinit var binding: FragmentAddEmployeeBinding
    val items1 = mutableListOf("Test 1.1", "Test 1.2", "Test 1.3", "Test 1.4", "Test 1.5")
    val items2 = mutableListOf("Test 2.1", "Test 2.2", "Test 2.3", "Test 2.4", "Test 2.5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAddEmployeeBinding.inflate(inflater)
        binding.sPost.adapter = AddEmployeeSpinnerAdapter(items1)
        binding.sTeam.adapter = AddEmployeeSpinnerAdapter(items2)
        binding.sTeam.dropDownVerticalOffset = binding.sTeam.top - binding.sTeam.bottom

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
