package com.vxplore.core.domain.useCasess

import android.content.Context
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.*
import com.vxplore.core.domain.repositoriess.OtpRepository
import com.vxplore.core.helpers.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OtpUseCases @Inject constructor(
    private val otpRepository: OtpRepository,
    private val pref: AppStore
    ) {

    fun verifyOtp(otp: String, number: String?) = flow {

        var tesTuserId= "1234"

        when (val response = otpRepository.otpDetailsRepo(otp)) {

            is Resource.Success -> {
                response.data?.apply {
                    when (status) {
                        true -> {
                            if (otp == otp_details[0].otp) {
                                if (number == otp_details[0].phone) {
                                    pref.login(tesTuserId)
                                    emit(Data(EmitType.Navigate, value = Destination.Dashboard()))
                                } else {
                                    emit(
                                        Data(
                                            EmitType.Navigate,
                                            value = Destination.MobileNo(number ?: "")
                                        )
                                    )
                                }


                            } else {
                                emit(Data(type = EmitType.ERROR, value = message))
                            }


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