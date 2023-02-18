package com.src.onboarding.data.remote.model.employee.team

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.employee.team.Team

class TeamMapper : Mapper<Team, TeamResponse> {
    override suspend fun mapFromResponseToModel(data: TeamResponse): Team {
        return Team(id = data.id, name = data.name)
    }
}