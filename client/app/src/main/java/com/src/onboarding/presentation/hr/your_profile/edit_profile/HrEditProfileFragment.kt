package com.src.onboarding.presentation.hr.your_profile.edit_profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.src.onboarding.databinding.FragmentEditProfileBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.user.EditProfileState
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.hr.your_profile.profile.viewModel.HrUserProfileViewModel
import com.src.onboarding.presentation.utils.PhotoCompression
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class HrEditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var viewModel: HrUserProfileViewModel
    private var photo: Uri? = null
    private var isClickNext = false

    val GALLERY_REQUEST_CODE = 1234

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        viewModel = (activity as HrActivity).getUserProfileViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvUserPicture.setOnClickListener {
            pickFromGallery()
        }
        setOnClickListenerForNextButton()
        viewModel.liveDataEditProfileState.observe(
            this.viewLifecycleOwner,
            this::checkEditProfileState
        )
        viewModel.liveDataLogoutState.observe(
            this.viewLifecycleOwner,
            this::checkLogoutState
        )
        binding.tvSignOut.setOnClickListener {
            viewModel.logout()
        }
        setData()
        setOnClickListenerForBackButton()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        val name = viewModel.getName()
        if (name != null) {
            binding.tvUserName.setText(name)
        } else {
            //TOdo обработка ошибки (имени нет)
        }
        val description = viewModel.getDescription()
        if (description != null) {
            binding.etAbout.setText(description)
        } else {
            //TOdo обработка ошибки (логина нет)
        }
        val image = viewModel.getImageString()
        if (image != null) {
            Glide.with(requireContext())
                .load(image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivUserPicture)
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data.let { uri ->
                        launchImageCrop(uri)
                    }
                } else {
                    Log.e(TAG, "Image selection error")
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                println(result.uri)
                if (resultCode == Activity.RESULT_OK) {
                    result.uri?.let {
                        setImage(it)
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.error}")
                }
            }
        }
    }

    private fun setImage(uri: Uri) {
        binding.ivUserPicture.setPadding(0, 0, 0, 0)
        Glide.with(this)
            .load(uri)
            .into(binding.ivUserPicture)
        photo = uri
        viewModel.setPhoto(uri)
    }

    private fun launchImageCrop(uri: Uri?) {
        CropImage.activity(uri)
            .setCropShape(CropImageView.CropShape.OVAL)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .start(requireContext(), this)
    }

    private fun setOnClickListenerForNextButton() {
        binding.tvSave.setOnClickListener {
            val name = binding.tvUserName.text.toString()

            val description =
                binding.etAbout.text.toString().replace("\\s".toRegex(), "")

            if (binding.tvUserName.text.toString()
                    .isNotEmpty() && binding.etAbout.text.toString()
                    .isNotEmpty()
            ) {
                val photoCompression = PhotoCompression()
                if (photo != null) {
                    photo = photoCompression.execute(requireContext(), photo!!)
                }
                viewModel.setDescription(description)
                viewModel.setName(name)
                viewModel.editProfile()
                isClickNext = true
            }
        }
    }

    //TODO обработка ошибок
    private fun checkEditProfileState(state: EditProfileState) {
        if (isClickNext) {
            isClickNext = false
            when (state) {
                is EditProfileState.SuccessState -> {
                    viewModel.setEditProfileState()
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is EditProfileState.LoginAlreadyExistsState -> println("такой логин уже существует")
                is EditProfileState.ErrorState -> println("ошибка сервера")
                else -> {
                }
            }
        }
    }

    private fun setOnClickListenerForBackButton() {
        binding.ivBackButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkLogoutState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
                startActivity(Intent(context, LoginActivity::class.java).apply {
                })
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {}
        }

    }

    companion object {
        private const val TAG = "AppDebug"
    }

}