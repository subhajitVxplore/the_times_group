package com.vxplore.core.domain.repositoriess

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.DonutChartModel
import com.vxplore.core.domain.model.VendorDetailsResponse

interface DonutChartDetailsRepository {
    suspend fun donutChartDetailsRepository(): Resource<DonutChartModel>
}