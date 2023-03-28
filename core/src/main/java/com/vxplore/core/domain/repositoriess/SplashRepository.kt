package com.vxplore.core.domain.repositoriess


import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.AppVersionResponse
import com.vxplore.core.domain.model.BaseUrlModel

interface SplashRepository {
    suspend fun appVersion(currentVersion: Int): Resource<AppVersionResponse>

    suspend fun baseUrl(): Resource<BaseUrlModel>
}