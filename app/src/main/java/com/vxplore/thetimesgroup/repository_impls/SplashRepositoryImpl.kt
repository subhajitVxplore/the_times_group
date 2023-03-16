package com.vxplore.thetimesgroup.repository_impls

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.AppVersionResponse
import com.vxplore.core.domain.repositoriess.SplashRepository
import com.vxplore.thetimesgroup.data.online.AppVersionApi

import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
        private val appVersionApi: AppVersionApi
): SplashRepository {
    override suspend fun appVersion(currentVersion: Int): Resource<AppVersionResponse> {
       return try {
            val  reslt = appVersionApi.getAppVersion();
            Resource.Success(reslt)
        } catch (ex: Exception) {
           Resource.Error(message = ex.message)
        }

        /*delay(2000L)
        return Resource.Success(
            AppVersionResponse(
                status = true,
                message = "Success",
                appVersion = AppVersion(
                    versionCode = 1,
                    releaseDate = "11/22/2022",
                    versionMessage = "This version is outdated",
                    isSkipable = false,
                    link = "asdsad"
                )
            )
        )*/
    }
}