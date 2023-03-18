package com.vxplore.thetimesgroup.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.Vendor
import com.vxplore.core.domain.useCasess.OtpUseCases
import com.vxplore.core.domain.useCasess.VendorDetailsUseCases
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val vendorDetailsUseCases: VendorDetailsUseCases,
private val appNavigator: AppNavigator
) :ViewModel() {
    private val _vendors = MutableStateFlow(emptyList<Vendor>())
    val vendors = _vendors.asStateFlow()

    //val vendorsQuery = mutableStateOf("")
    init {
        getVendors()
    }
    fun onDashboardToBilling() {
        appNavigator.tryNavigateTo(
            route = Destination.Billing(),
           // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }
    fun getVendors() {
        vendorDetailsUseCases.getVendors()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.VENDORS -> {
                        it.value?.castListToRequiredTypes<Vendor>()?.let {vendors ->
                            _vendors.update { vendors }
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


// fun searchVendors(updatedVendor: String) {
//        vendorDetailsUseCases.searchVendors(updatedVendor)
//            .flowOn(Dispatchers.IO)
//            .onEach {
//                when (it.type) {
//                    EmitType.VENDORS -> {
//                        it.value?.castListToRequiredTypes<Vendor>()?.let {vendors ->
//                            _vendors.update { vendors }
//                        }
//                    }
//
//                    EmitType.NetworkError -> {
//                        it.value?.apply {
//                            castValueToRequiredTypes<String>()?.let {
//
//                            }
//                        }
//                    }
//                    else -> {}
//                }
//            }.launchIn(viewModelScope)
//    }

}