package com.vxplore.core.domain.useCasess

import com.vxplore.core.common.Data
import com.vxplore.core.common.EmitType
import com.vxplore.core.common.Resource
import com.vxplore.core.common.handleFailedResponse
import com.vxplore.core.domain.repositoriess.VendorDetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VendorDetailsUseCases @Inject constructor(private val vendorDetailsRepository: VendorDetailsRepository){


    fun getVendors() = flow{
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


//    fun searchVendors(updatedVendor: String) = flow{
//        when (val response = vendorDetailsRepository.vendorDetailsRepo()) {
//            is Resource.Success -> {
//                response.data?.apply {
//                    when (status) {
//                        true -> {
//                            if (updatedVendor.equals(vendor_list)){
//                                emit(Data(type = EmitType.VENDORS, value = vendor_list))
//                            }
//
//                        }
//                        else -> {
//                            emit(Data(type = EmitType.BackendError, value = message))
//                        }
//                    }
//                }
//            }
//            is Resource.Error -> {
//                handleFailedResponse(
//                    response = response,
//                    message = response.message,
//                    emitType = EmitType.NetworkError
//                )
//            }
//            else -> {
//
//            }
//        }
//    }


}