package com.src.onboarding.presentation.profile

import android.graphics.Color
import android.opengl.Visibility
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {
    lateinit var binding: FragmentUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonOnClick()

        binding.tvAchievements.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.ivMan.visibility = View.VISIBLE

            binding.tvActivity.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            binding.tvStatistics.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            binding.tvAchievements.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_purple))
        }

        binding.tvActivity.setOnClickListener {
            binding.rvActivity.visibility = View.VISIBLE
            binding.rvStatistics.visibility = View.INVISIBLE
            binding.ivMan.visibility = View.INVISIBLE

            binding.tvActivity.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_purple))
            binding.tvStatistics.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            binding.tvAchievements.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        }

        binding.tvStatistics.setOnClickListener {
            binding.rvActivity.visibility = View.INVISIBLE
            binding.rvStatistics.visibility = View.VISIBLE
            binding.ivMan.visibility = View.INVISIBLE

            binding.tvActivity.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            binding.tvStatistics.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_purple))
            binding.tvAchievements.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        }

    }

    private fun setBackButtonOnClick() {
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val USER_ID = "user_id"
    }
}