package com.src.onboarding.presentation.support.support_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentClientSupportBinding
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.support.new_appeal.NewAppealFragment

class ClientSupportFragment : Fragment() {
    private lateinit var binding: FragmentClientSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientSupportBinding.inflate(inflater)
        if (activity is HrActivity)
            binding.cvAddAppeal.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnAddAppealButtonClick()
    }

    private fun setOnAddAppealButtonClick() {
        binding.cvAddAppeal.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewAppealFragment())
        }
    }
}
