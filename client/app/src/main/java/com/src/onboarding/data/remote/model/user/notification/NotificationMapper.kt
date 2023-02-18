package com.src.onboarding.data.remote.model.user.notification

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.user.Notification

class NotificationMapper : Mapper<Notification, NotificationResponse> {
    override suspend fun mapFromResponseToModel(data: NotificationResponse): Notification {
        return Notification(id = data.id, content = data.content, date = data.date)
    }
}