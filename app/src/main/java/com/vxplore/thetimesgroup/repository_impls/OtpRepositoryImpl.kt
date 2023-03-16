package com.vxplore.thetimesgroup.repository_impls

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.OtpDtailsResponse
import com.vxplore.core.domain.repositoriess.OtpRepository
import com.vxplore.thetimesgroup.data.online.AppVersionApi
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(private val appVersionApi: AppVersionApi):OtpRepository {

    override suspend fun otpDetailsRepo(myOtp: String): Resource<OtpDtailsResponse> {
        return try {
            val  reslt = appVersionApi.getOtpDetails();
            Resource.Success(reslt)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}