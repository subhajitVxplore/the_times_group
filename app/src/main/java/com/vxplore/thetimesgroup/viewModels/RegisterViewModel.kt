package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    var yourNameText = mutableStateOf("")
    var emailAddressText = mutableStateOf("")
    var addressText = mutableStateOf("")
    var pincodeText = mutableStateOf("")
    var districtText = mutableStateOf("")
    var stateText = mutableStateOf("")

}