package com.src.onboarding.data.remote.model.user.user_profile

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.di.NetworkModule
import com.src.onboarding.domain.model.user.UserProfile

class UserProfileMapper : Mapper<UserProfile, UserProfileResponse> {
    override suspend fun mapFromResponseToModel(data: UserProfileResponse): UserProfile {
        return UserProfile(
            id = data.id,
            image = "${NetworkModule.BASE_URL}${NetworkModule.USER_SERVICE_BASE_URL}${data.image}",
            post = data.post,
            team = data.team,
            email = data.email,
            description = data.description,
            name = data.name
        )
    }
}