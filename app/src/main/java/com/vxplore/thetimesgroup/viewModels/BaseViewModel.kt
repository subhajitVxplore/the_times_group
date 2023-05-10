package com.vxplore.thetimesgroup.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
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
  var bitmapImg = mutableStateListOf<Bitmap>()
  // var bitmapImg = mutableStateListOf<Bitmap>()
   // var bitmapImg: MutableState<Bitmap?> = mutableStateOf(null)
}