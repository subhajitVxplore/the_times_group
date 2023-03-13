package com.vxplore.core.domain.repositoriess


import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.AppVersionResponse

interface SplashRepository {
    suspend fun appVersion(currentVersion: Int): Resource<AppVersionResponse>
}