package com.src.onboarding.domain.usecase.user

import android.net.Uri
import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.user.EditProfileState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class EditProfileUseCase(private val userRepository: UserRepository) {
    suspend fun execute(description: String, name: String, uri: Uri?): EditProfileState =
        withContext(Dispatchers.IO) {
            val json = JSONObject(
                mapOf(
                    "description" to description,
                    "name" to name,
                )
            )
            if (uri == null) {
                return@withContext userRepository.editProfile(json.toString(), null)
            }
            val file = uri.path?.let { File(it) }
            return@withContext userRepository.editProfile(json.toString(), file)
        }
}