package com.src.onboarding.data.remote.model.course.colleague

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.di.NetworkModule
import com.src.onboarding.domain.model.course.colleague.Colleague

class ColleagueMapper : Mapper<Colleague, ColleagueResponse> {
    override suspend fun mapFromResponseToModel(data: ColleagueResponse): Colleague {
        return Colleague(
            id = data.id,
            name = data.name,
            image = "${NetworkModule.BASE_URL}${NetworkModule.USER_SERVICE_BASE_URL}${data.image}",
            post = data.post
        )
    }
}