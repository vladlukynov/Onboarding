package com.src.onboarding.data.remote.model.user.activity

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.user.Activity

class ActivityMapper : Mapper<Activity, ActivityResponse> {
    override suspend fun mapFromResponseToModel(data: ActivityResponse): Activity {
        return Activity(
            id = data.id,
            content = data.content,
            date = data.date,
            percent = data.percent
        )
    }
}