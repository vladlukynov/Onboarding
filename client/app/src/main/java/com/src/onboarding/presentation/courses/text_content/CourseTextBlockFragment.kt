package com.src.onboarding.presentation.courses.text_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentCourseTextBlockBinding

class CourseTextBlockFragment : Fragment() {
    private lateinit var binding: FragmentCourseTextBlockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseTextBlockBinding.inflate(inflater)
        return binding.root
    }
}
