package com.vxplore.thetimesgroup.viewModels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Action
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.Pincode
import com.vxplore.core.domain.model.Pincodes
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.core.domain.model.SearchVendorModel
import com.vxplore.core.domain.useCasess.AddVendorUseCases
import com.vxplore.core.domain.useCasess.RegisterUsecases
import com.vxplore.thetimesgroup.custom_views.UiData
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.helpers_impl.SavableMutableState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class AddVendorViewModel @Inject constructor(
    private val addVendorUseCases: AddVendorUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var yourNameText = mutableStateOf("")
    var mobileText = mutableStateOf("")
    var emailAddressText = mutableStateOf("")
    var addressText = mutableStateOf("")

    private val _pincodes = MutableStateFlow(emptyList<Pincodes>())
    val pincodes = _pincodes.asStateFlow()
    var selectedPincode = mutableStateOf("")
    var previousSelectedPincode = mutableStateOf("")

    private val _suggestionsss: MutableStateFlow<List<Pincodes>> = MutableStateFlow(emptyList())
    var suggestionListVisibility by  mutableStateOf(false)
    private var suggestionsBackup: List<Pincodes> = emptyList()
    val isFocused = mutableStateOf(false)
    var mExpanded by  mutableStateOf(false)



    val stateLoading = SavableMutableState(
        key = UiData.StateApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    init {
       // getPincodesByDistributorId("")
    }

    fun onAddVendorToAddVendorSuccess() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendorSuccess(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }


    fun addVendor() {
        addVendorUseCases.addVendor(
            yourNameText.value, mobileText.value, emailAddressText.value, selectedPincode.value
        ).flowOn(Dispatchers.IO).onEach {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let { destination ->
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

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun clearPincodesQuery() {
        selectedPincode.value = ""
        suggestionListVisibility = false
        viewModelScope.launch {
            suggestionsBackup.apply {
                _suggestionsss.emit(this)
            }
        }
    }



    fun getPincodesByDistributorId(query: String) {


        addVendorUseCases.getPincodesByDistributorId(query).flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                // pinCodeLoading.setValue(it)
                            }
                        }
                    }
                    EmitType.Pincodes -> {
                        it.value?.castListToRequiredTypes<Pincodes>()?.let { pincodes ->
                            _pincodes.update { pincodes }
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




    fun onSelectPincode(pincodes: Pincodes) {
        selectedPincode.value=previousSelectedPincode.value
        previousSelectedPincode.value = "${selectedPincode.value},${pincodes.pincode},"
        selectedPincode.value=""
    }
}