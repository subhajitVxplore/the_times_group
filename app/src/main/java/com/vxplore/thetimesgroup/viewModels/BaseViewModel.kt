package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
   val appNavigator: AppNavigator,
) : ViewModel() {

   var contextt= mutableStateOf(LocalContext)

}