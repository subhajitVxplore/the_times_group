package com.vxplore.core.domain.useCasess

import android.content.Context
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.*
import com.vxplore.core.domain.repositoriess.OtpRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OtpUseCases @Inject constructor(
    private val otpRepository: OtpRepository,
    private val appNavigator: AppNavigator,

) {

    fun verifyOtp(otp: String, number: String?, mContext: Context) = flow {

        when (val response = otpRepository.otpDetailsRepo(otp)) {

            is Resource.Success -> {
                response.data?.apply {
                    when (status) {
                        true -> {

                            if (otp == otp_details[0].otp) {

                                if (number==otp_details[0].phone){
                                    appNavigator.navigateTo(
                                        route = Destination.Dashboard.fullRoute,
                                        popUpToRoute = Destination.Otp(number.toString()),
                                        isSingleTop = true,
                                        inclusive = true
                                    )
                                }else{
                                    appNavigator.navigateTo(
                                        route = Destination.Register.fullRoute,
                                        popUpToRoute = Destination.Otp(number.toString()),
                                        isSingleTop = true,
                                        inclusive = true
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