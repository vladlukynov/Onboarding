package com.src.onboarding.data.remote.utils

interface Mapper<M, R> {
    suspend fun mapFromResponseToModel(data: R): M
}