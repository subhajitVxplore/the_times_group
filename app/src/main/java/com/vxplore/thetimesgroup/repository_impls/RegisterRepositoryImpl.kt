package com.vxplore.thetimesgroup.repository_impls

import com.vxplore.core.common.Resource
import com.vxplore.core.domain.model.AllStatesModel
import com.vxplore.core.domain.model.DistrictByStateModel
import com.vxplore.core.domain.repositoriess.MobileNoScreenRepository
import com.vxplore.core.domain.repositoriess.RegisterRepository
import com.vxplore.thetimesgroup.mainController.MyApiList
import kotlinx.coroutines.delay
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val myApiList: MyApiList):RegisterRepository {

    override suspend fun getAllStateRepository(): Resource<AllStatesModel> {
      //  delay(2000L)
        return try {
            val  reslt = myApiList.getAllState()
            Resource.Success(reslt)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun getDistrictByStateRepository(state_id: String): Resource<DistrictByStateModel> {
      //  delay(2000L)
        return try {
            val  reslt = myApiList.getDistrictByState(state_id)
            Resource.Success(reslt)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

}