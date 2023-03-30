package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.State
import com.vxplore.core.domain.model.Vendor
import com.vxplore.core.domain.useCasess.RegisterUsecases
import com.vxplore.core.domain.useCasess.VendorDetailsUseCases
import com.vxplore.thetimesgroup.custom_views.UiData
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.helpers_impl.SavableMutableState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsecases: RegisterUsecases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    var yourNameText = mutableStateOf("")
    var emailAddressText = mutableStateOf("")
    var addressText = mutableStateOf("")
    var pincodeText = mutableStateOf("")
    var districtText = mutableStateOf("")
    var stateText = mutableStateOf("")
    private val _states = MutableStateFlow(emptyList<State>())
    val states = _states.asStateFlow()
    private val _districts = MutableStateFlow(emptyList<State>())
    val districts = _districts.asStateFlow()
    var selectedDistrict = mutableStateOf("")


    init {
        getAllStates()
        getDistrictByStateState(selectedDistrict.value)
    }


    val stateLoading = SavableMutableState(
        key = UiData.StateApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    val districtLoading = SavableMutableState(
        key = UiData.DistrictApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    val pinCodeLoading = SavableMutableState(
        key = UiData.PincodeApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    fun getAllStates() {
        registerUsecases.getAllState()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                stateLoading.setValue(it)
                            }
                        }
                    }

                    EmitType.States -> {
                        it.value?.castListToRequiredTypes<State>()?.let { states ->
                            _states.update { states }
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

    fun getDistrictByStateState(state: String) {
        registerUsecases.getDistrictByStateState(state)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                districtLoading.setValue(it)
                            }
                        }
                    }

                    EmitType.Districts -> {
                        it.value?.castListToRequiredTypes<State>()?.let { districts ->
                            _districts.update { districts }
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
            }
            .launchIn(viewModelScope)
    }






}