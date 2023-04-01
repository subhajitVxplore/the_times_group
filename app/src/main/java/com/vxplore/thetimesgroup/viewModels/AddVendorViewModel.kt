package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.Pincode
import com.vxplore.core.domain.useCasess.AddVendorUseCases
import com.vxplore.core.domain.useCasess.RegisterUsecases
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddVendorViewModel @Inject constructor(private val addVendorUseCases: AddVendorUseCases, private val appNavigator: AppNavigator,savedStateHandle: SavedStateHandle) : ViewModel(){


    var yourNameText = mutableStateOf("")
    var mobileText = mutableStateOf("")
    var emailAddressText = mutableStateOf("")
    var addressText = mutableStateOf("")

    private val _pincodes = MutableStateFlow(emptyList<Pincode>())
    val pincodes = _pincodes.asStateFlow()
    var selectedPincode = mutableStateOf("")



    fun onAddVendorToAddVendorSuccess() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendorSuccess(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }


    fun addVendor() {
        addVendorUseCases.addVendor(yourNameText.value,mobileText.value,emailAddressText.value,selectedPincode.value)
            .flowOn(Dispatchers.IO).onEach {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let { destination->
                                appNavigator.tryNavigateTo(
                                    destination,
                                    popUpToRoute = Destination.Register.fullRoute,
                                    isSingleTop = true,
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