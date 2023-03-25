package com.vxplore.thetimesgroup.viewModels

import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class VendorAddSuccessViewModel @Inject constructor(private val appNavigator: AppNavigator) :
    ViewModel() {


    fun onAddVendorSuccessToAddVendor() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendor(),
            popUpToRoute = Destination.AddVendorSuccess(),
            isSingleTop = true,
            inclusive = true
        )
    }

    fun onAddVendorSuccessToDashboard() {
        appNavigator.tryNavigateBack(
            route = Destination.Dashboard(),
//            popUpToRoute = Destination.AddVendorSuccess(),
//            isSingleTop = true,
            //inclusive = true
        )
    }
}