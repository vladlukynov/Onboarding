package com.src.onboarding.presentation.courses.add_employee.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.src.onboarding.databinding.ViewHolderDropdownItemBinding

class AddEmployeeSpinnerAdapter(var list: MutableList<String>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = 0

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ViewHolderDropdownItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.tvTitle.text = getItem(position) as String
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ViewHolderDropdownItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.ivPicture.visibility = View.GONE
        binding.tvTitle.text = getItem(position) as String
        return binding.root
    }
}
