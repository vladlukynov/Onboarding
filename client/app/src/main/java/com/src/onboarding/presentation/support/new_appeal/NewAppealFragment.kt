package com.src.onboarding.presentation.support.new_appeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentNewAppealBinding

class NewAppealFragment : Fragment() {
    private lateinit var binding: FragmentNewAppealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewAppealBinding.inflate(inflater)
        return binding.root
    }
}
