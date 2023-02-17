package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.domain.model.login.Login
import com.src.onboarding.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {
    override suspend fun signIn(data: Login) = withContext(Dispatchers.IO) {
        return@withContext loginDataSource.signIn(data)
    }
}