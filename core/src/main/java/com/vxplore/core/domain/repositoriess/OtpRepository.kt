package com.vxplore.core.domain.repositoriess

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.OtpDtailsResponse

interface OtpRepository {

    suspend fun otpDetailsRepo(myOtp: String): Resource<OtpDtailsResponse>
}