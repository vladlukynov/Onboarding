package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.domain.repository.UserRepository

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {
}