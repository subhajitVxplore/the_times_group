package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor():ViewModel() {


    fun search(updatedAddress: String) {

    }

    var showClearButton = mutableStateOf(false)
}