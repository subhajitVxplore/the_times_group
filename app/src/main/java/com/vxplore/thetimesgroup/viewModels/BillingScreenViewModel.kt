package com.vxplore.thetimesgroup.viewModels

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor():ViewModel() {


    fun search(updatedAddress: String) {

    }

    var showClearButton = mutableStateOf(false)
    var expand =mutableStateOf(false)  // Expand State
    var stroke = mutableStateOf(1)
}