package com.src.onboarding.presentation.courses.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.src.onboarding.databinding.FragmentTestBinding
import com.src.onboarding.presentation.courses.test.adapter.TestAnswerAdapter

class TestFragment : Fragment() {
    lateinit var binding: FragmentTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterForAnswers()
    }

    private fun setAdapterForAnswers() {
        val adapter = TestAnswerAdapter()
        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvAnswers.layoutManager = layoutManager
        binding.rvAnswers.adapter = adapter
    }

}