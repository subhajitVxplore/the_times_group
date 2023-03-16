package com.vxplore.thetimesgroup.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.useCasess.OtpUseCases
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val otpUseCases: OtpUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(){



    val number = savedStateHandle.get<String>(Destination.MobileNo.MOBILE_N)
    fun onOtpToMob(mobileNumber: String) {
        Log.d("MOBILE", mobileNumber)
        appNavigator.tryNavigateTo(
            route = Destination.MobileNo(mobileNumber),
            popUpToRoute = Destination.Otp(mobileNumber),
            isSingleTop = true,
            inclusive = true
        )
    }
    fun onOtpToRegister(mobileNumber: String) {
        appNavigator.tryNavigateTo(
            route = Destination.Register(),
            popUpToRoute = Destination.Otp(mobileNumber),
            isSingleTop = true,
            inclusive = true
        )
    }


fun verifyOtp(otpp: String, mContext: Context) {

        otpUseCases.verifyOtp(otpp,number,mContext)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Otp -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let {
                                appNavigator.navigateTo(
                                    it(),
                                    popUpToRoute = Destination.Otp(number.toString()),
                                    inclusive = true
                                )
                            }
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }



}