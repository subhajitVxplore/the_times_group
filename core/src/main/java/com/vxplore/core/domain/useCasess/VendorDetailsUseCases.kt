package com.vxplore.core.domain.useCasess

import android.content.Context
import android.widget.Toast
import com.vxplore.core.common.*
import com.vxplore.core.domain.repositoriess.OtpRepository
import com.vxplore.core.domain.repositoriess.VendorDetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VendorDetailsUseCases @Inject constructor(private val vendorDetailsRepository: VendorDetailsRepository){


    fun getVendors() = flow{
        when (val response = vendorDetailsRepository.vendorDetailsRepo()) {
            is Resource.Success -> {
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(Data(type = EmitType.VENDORS, value = vendor_list))
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