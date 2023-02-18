package com.src.onboarding.data.remote.dataSource.user

import com.src.onboarding.data.remote.model.user.notification.NotificationMapper
import com.src.onboarding.data.remote.service.UserService
import com.src.onboarding.domain.user.Notification
import com.src.onboarding.domain.state.login.BasicState

class UserDataSourceImpl(
    private val userService: UserService,
    private val notificationMapper: NotificationMapper
) : UserDataSource {
    override suspend fun getNotifications(): BasicState<List<Notification>> {
        val response = userService.getNotifications()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map {
                    notificationMapper.mapFromResponseToModel(
                        it
                    )
                })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getCountNotification(): BasicState<Long> {
        val response = userService.getCountNotification()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body)
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun clearNotifications(): BasicState<Unit> {
        val response = userService.getCountNotification()
        if (response.isSuccessful) {
            return BasicState.SuccessState(Unit)
        }
        return BasicState.ErrorState()
    }
}