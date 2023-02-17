package com.src.onboarding.data.remote.model.login.login

import com.src.onboarding.domain.model.login.Login

class LoginMapper {
     fun mapFromModelToResponse(login: Login) = LoginResponse(
        email = login.email,
        password = login.password
    )
}