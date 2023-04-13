package com.vxplore.core.domain.useCasess

import com.vxplore.core.common.*
import com.vxplore.core.domain.repositoriess.DonutChartDetailsRepository
import com.vxplore.core.domain.repositoriess.VendorDetailsRepository
import com.vxplore.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardUseCases @Inject constructor(
    private val pref: AppStore,
    private val vendorDetailsRepository: VendorDetailsRepository,
    private val donutChartDetailsRepository: DonutChartDetailsRepository
) {

    fun logOutFromDashboard() = flow {
        pref.logout()
        emit(Data(type = EmitType.Navigate, Destination.MobileNo))
    }


    fun getVendors() = flow {
        emit(Data(EmitType.Loading, true))
        when (val response = vendorDetailsRepository.vendorDetailsRepo()) {
            is Resource.Success -> {
                emit(Data(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(Data(type = EmitType.VENDORS, value = top_vendors))
                        }
                        else -> {
                            emit(Data(type = EmitType.BackendError, value = message))
                        }
                    }
                }
            }
            is Resource.Error -> {

                handleFailedResponse(
                    response = response,
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }
            else -> {

            }
        }
    }


    fun getDonutChartData() = flow {
        emit(Data(EmitType.Loading, true))
        when (val response = donutChartDetailsRepository.donutChartDetailsRepository()) {
            is Resource.Success -> {
                emit(Data(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(Data(type = EmitType.PAPER_CODE, value = papers))
                            emit(Data(type = EmitType.TOTAL_SOLD_PAPER, value = total_sold_papers))
                        }
                        else -> {
                            emit(Data(type = EmitType.BackendError, value = message))
                        }
                    }
                }
            }
            is Resource.Error -> {

                handleFailedResponse(
                    response = response,
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }
            else -> {

            }
        }
    }


}