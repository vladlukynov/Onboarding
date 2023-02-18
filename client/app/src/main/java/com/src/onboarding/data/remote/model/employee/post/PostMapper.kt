package com.src.onboarding.data.remote.model.employee.post

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.employee.post.Post

class PostMapper : Mapper<Post, PostResponse> {
    override suspend fun mapFromResponseToModel(data: PostResponse): Post {
        return Post(id = data.id, name = data.name)
    }
}