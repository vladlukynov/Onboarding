package com.src.onboarding.di

import com.src.onboarding.data.remote.model.course.colleague.ColleagueMapper
import com.src.onboarding.data.remote.model.course.course.CourseMapper
import com.src.onboarding.data.remote.model.course.mainCourse.MainCourseMapper
import com.src.onboarding.data.remote.model.employee.post.PostMapper
import com.src.onboarding.data.remote.model.employee.team.TeamMapper
import com.src.onboarding.data.remote.model.login.login.LoginMapper
import com.src.onboarding.data.remote.model.task.TaskMapper
import com.src.onboarding.data.remote.model.user.activity.ActivityMapper
import com.src.onboarding.data.remote.model.user.notification.NotificationMapper
import com.src.onboarding.data.remote.model.user.user_profile.UserProfileMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {
    @Singleton
    @Provides
    fun provideLoginMapper(): LoginMapper {
        return LoginMapper()
    }

    @Singleton
    @Provides
    fun provideColleagueMapper(): ColleagueMapper {
        return ColleagueMapper()
    }

    @Singleton
    @Provides
    fun providePostMapper(): PostMapper {
        return PostMapper()
    }

    @Singleton
    @Provides
    fun provideNotificationMapper(): NotificationMapper {
        return NotificationMapper()
    }

    @Singleton
    @Provides
    fun provideTeamMapper(): TeamMapper {
        return TeamMapper()
    }

    @Singleton
    @Provides
    fun provideCourseMapper(): CourseMapper {
        return CourseMapper()
    }

    @Singleton
    @Provides
    fun provideMainCourseMapper(courseMapper: CourseMapper): MainCourseMapper {
        return MainCourseMapper(courseMapper = courseMapper)
    }

    @Singleton
    @Provides
    fun provideTaskMapper(): TaskMapper {
        return TaskMapper()
    }

    @Singleton
    @Provides
    fun provideUserProfileMapper(): UserProfileMapper {
        return UserProfileMapper()
    }

    @Singleton
    @Provides
    fun provideActivityMapper(): ActivityMapper {
        return ActivityMapper()
    }
}