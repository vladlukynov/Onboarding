package com.src.onboarding.domain.usecase.employee.post

import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.repository.EmployeeRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostsUseCase(private val employeeRepository: EmployeeRepository) {
    suspend fun execute(): BasicState<List<Post>> = withContext(Dispatchers.IO) {
        return@withContext employeeRepository.getPosts()
    }
}