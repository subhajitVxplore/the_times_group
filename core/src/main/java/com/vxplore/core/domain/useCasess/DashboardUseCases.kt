package com.vxplore.core.domain.useCasess

import com.vxplore.core.common.Data
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardUseCases @Inject constructor(private val pref: AppStore){

    fun logOutFromDashboard() = flow {
        pref.logout()
        emit(Data(type = EmitType.Navigate, Destination.MobileNo))
    }



}