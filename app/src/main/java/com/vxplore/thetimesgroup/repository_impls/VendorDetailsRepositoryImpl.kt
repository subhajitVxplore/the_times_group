package com.vxplore.thetimesgroup.repository_impls

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.VendorDetailsResponse
import com.vxplore.core.domain.repositoriess.VendorDetailsRepository
import com.vxplore.thetimesgroup.data.online.AppVersionApi
import javax.inject.Inject

class VendorDetailsRepositoryImpl @Inject constructor(private val appVersionApi: AppVersionApi) :
    VendorDetailsRepository {
    override suspend fun vendorDetailsRepo(): Resource<VendorDetailsResponse> {
        return try {
            val reslt = appVersionApi.getVendorDetails()
            Resource.Success(reslt)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}