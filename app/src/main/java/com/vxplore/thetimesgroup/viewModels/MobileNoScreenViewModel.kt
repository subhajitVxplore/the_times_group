package com.vxplore.thetimesgroup.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MobileNoScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var MobileNoText = mutableStateOf("")


    init {
        val number = savedStateHandle.get<String>(Destination.Otp.MOBILE_NO)
        number?.let {
            if (it.length == 1) {
                MobileNoText.value = ""
            } else {
                MobileNoText.value = it
            }
        }
    }

    // private var mobileNumber = "HelloWorld"
    fun onMobToOtp(mobileNumber: String) {
        appNavigator.tryNavigateTo(
            route = Destination.Otp(mobileNumber),
            popUpToRoute = Destination.MobileNo(mobileNumber),
            isSingleTop = true,
            inclusive = true
        )
    }

    fun exit() {
        //appNavigator.tryNavigateBack(route = Destination.Splash.fullRoute)
        appNavigator.tryNavigateBack()

        //appNavigator.navigateTo(destination(mobileNumber))
    }

}