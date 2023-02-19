package com.src.onboarding.presentation.welcome.name_registration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentNameRegistrationBinding
import com.src.onboarding.domain.state.login.RegistrationState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.utils.PhotoCompression
import com.src.onboarding.presentation.utils.REGEX_SPACE
import com.src.onboarding.presentation.welcome.code_enter.CodeEnterFragment
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*

class NameRegistrationFragment : Fragment() {
    private lateinit var binding: FragmentNameRegistrationBinding
    private lateinit var bindingLoading: FragmentLoadingBinding
    private lateinit var viewModel: RegistrationViewModel
    private var photo: Uri? = null
    val GALLERY_REQUEST_CODE = 1234

    private var isClickNext = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameRegistrationBinding.inflate(inflater)
        viewModel = (activity as LoginActivity).getRegistrationViewModel()
        bindingLoading = binding.loginLoading
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (viewModel.imageIsNotEmpty()) {
            val image = viewModel.liveDataImage.value
            binding.ivChangeAvatar.setPadding(0, 0, 0, 0)
            Glide.with(this)
                .load(image)
                .into(binding.ivChangeAvatar)
        }
        binding.cdAddPicture.setOnClickListener {
            pickFromGallery()
        }
        viewModel.liveDataRegistration.observe(this.viewLifecycleOwner, this::checkRegistration)
        viewModel.liveDataIsLoading.observe(this.viewLifecycleOwner, this::checkLoading)
        setOnClickListenerForNextButton()
    }

    @SuppressLint("IntentReset")
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
        binding.ivChangeAvatar.setPadding(0, 0, 0, 0)
        photo = uri
        Glide.with(this)
            .load(uri)
            .into(binding.ivChangeAvatar)
    }

    private fun launchImageCrop(uri: Uri?) {
        CropImage.activity(uri)
            .setCropShape(CropImageView.CropShape.OVAL)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .start(requireContext(), this)
    }

    private fun checkRegistration(state: RegistrationState) {
        if (isClickNext) {
            isClickNext = false
            when (state) {
                is RegistrationState.SuccessState -> {
                    (activity as LoginActivity).replaceFragment(CodeEnterFragment())
                }
                is RegistrationState.EmailAlreadyExistsState -> {
                    (activity as LoginActivity).showSnackBar(getString(R.string.email_already_exists))
                }
                is RegistrationState.ErrorState -> {
                    (activity as LoginActivity).showSnackBar(null)
                }
                else -> {

                }
            }
        }
    }

    private fun setOnClickListenerForNextButton() {
        binding.tvSaveButton.setOnClickListener {
            val firstNameWithoutSpace = binding.etFirstName.text.toString()
                .replace(REGEX_SPACE, " ")
                .lowercase(Locale.getDefault())
                .trim()
            val lastNameWithoutSpace = binding.etLastName.text.toString()
                .replace(REGEX_SPACE, " ")
                .lowercase(Locale.getDefault())
                .trim()
            if (firstNameWithoutSpace.isNotEmpty() && lastNameWithoutSpace.isNotEmpty()
            ) {
                viewModel.setName("$firstNameWithoutSpace $lastNameWithoutSpace")
                if (viewModel.liveDataName.value == null || viewModel.liveDataEmail.value == null || viewModel.liveDataPassword.value == null) {
                    (activity as LoginActivity).showSnackBar(null)
                } else {
                    val photoCompression = PhotoCompression()
                    if (photo != null) {
                        photo = photoCompression.execute(requireContext(), photo!!)
                        viewModel.setImage(uri = photo!!)
                    }
                    viewModel.registration(photo)
                }
            } else {
                (activity as LoginActivity).showSnackBar(getString(R.string.fill_all_fields))
            }

            isClickNext = true
        }
    }

    private fun checkLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingLoading.clLoadingPage.visibility = View.VISIBLE
        } else {
            bindingLoading.clLoadingPage.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "AppDebug"
    }
}