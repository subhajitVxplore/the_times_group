package com.vxplore.thetimesgroup.viewModels

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.core.common.DialogData
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.Vendor
import com.vxplore.core.domain.useCasess.DashboardUseCases
import com.vxplore.core.domain.useCasess.VendorDetailsUseCases
import com.vxplore.thetimesgroup.custom_views.UiData
import com.vxplore.thetimesgroup.extensions.MyDialog
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.helpers_impl.SavableMutableState
import com.vxplore.thetimesgroup.screens.PaperSold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vendorDetailsUseCases: VendorDetailsUseCases,
    private val dashBoardUseCases: DashboardUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _vendors = MutableStateFlow(emptyList<Vendor>())
    val vendors = _vendors.asStateFlow()
    val dashboardBack = mutableStateOf<MyDialog?>(null)
    var values = listOf<PaperSold>()
    var sumOfValues = mutableStateOf(0f)

   // values: List<Float> = listOf(10f, 20f, 30f,40f,50f),

    //val vendorsQuery = mutabletateOf("")
    init {
        getVendors()
        calculateDonutSweepAngles()
    }

    fun calculateDonutSweepAngles(){
        values.forEach{
            sumOfValues.value += it.floatValue

        }

    }

    fun onDashboardToBilling() {
        appNavigator.tryNavigateTo(
            route = Destination.Billing(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }

    val vendorsLoading = SavableMutableState(
        key = UiData.LoginApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )


    fun onDashboardExit() {
        appNavigator.tryNavigateTo(
            route = Destination.Billing(),
             popUpToRoute = Destination.Dashboard(),
            isSingleTop = true,
            inclusive = true
        )
    }
    fun getVendors() {
        vendorDetailsUseCases.getVendors()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                vendorsLoading.setValue(it)
                            }
                        }
                    }
                    EmitType.VENDORS -> {
                        it.value?.castListToRequiredTypes<Vendor>()?.let { vendors ->
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

    fun onBackDialog() {
            dashboardBack.value = MyDialog(
                data = DialogData(
                    title = "The TimesGroup",
                    message = "Are you sure you want to exit ?",
                    positive = "Yes",
                    negative = "No",
                )
            )
            handleDialogEvents()
    }


    private fun handleDialogEvents() {
        dashboardBack.value?.onConfirm = {

        }
        dashboardBack.value?.onDismiss = {
            dashboardBack.value?.setState(MyDialog.Companion.State.DISABLE)
        }
    }


    fun onLogoutFromDashboard() {
        viewModelScope.launch {
            dashBoardUseCases.logOutFromDashboard().collect {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Destination>()?.let {
                                //  scaffoldState.drawerState.close()
                                appNavigator.navigateTo(
                                    it.fullRoute,
                                    popUpToRoute = Destination.Dashboard(),
                                    inclusive = true,
                                    isSingleTop = true)
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
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


