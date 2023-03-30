package com.vxplore.core.domain.repositoriess

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.AllStatesModel
import com.vxplore.core.domain.model.DistrictByStateModel
import com.vxplore.core.domain.model.SendOtpModel

interface RegisterRepository {

    suspend fun getAllStateRepository(): Resource<AllStatesModel>

    suspend fun getDistrictByStateRepository(state_id: String): Resource<DistrictByStateModel>
}