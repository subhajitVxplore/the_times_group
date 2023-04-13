package com.vxplore.thetimesgroup.repository_impls

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.DonutChartModel
import com.vxplore.core.domain.repositoriess.DonutChartDetailsRepository
import com.vxplore.core.domain.repositoriess.VendorDetailsRepository
import com.vxplore.thetimesgroup.data.online.AppVersionApi
import javax.inject.Inject

class DonutChartDetailsRepositoryImpl @Inject constructor(private val appVersionApi: AppVersionApi) :
    DonutChartDetailsRepository {
    override suspend fun donutChartDetailsRepository(): Resource<DonutChartModel> {
        return try {
            val reslt = appVersionApi.getDonutChartDetails()
            Resource.Success(reslt)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}