package com.vxplore.thetimesgroup.viewModels

import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddVendorViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel(){

    fun onAddVendorToAddVendorSuccess() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendorSuccess(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }
}