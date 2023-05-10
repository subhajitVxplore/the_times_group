package com.vxplore.thetimesgroup.viewModels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BillPreviewScreenViewModel @Inject constructor(private val appNavigator: AppNavigator) :
    ViewModel() {


   // var bitmapImg :List<Bitmap>?=null


    // var bitmapImg: MutableState<Bitmap?> = mutableStateOf(null)
//    val images = remember {
//        mutableStateListOf<Bitmap>()
//    }
//    fun onBillingToAddVendor() {
//        appNavigator.tryNavigateTo(
//            route = Destination.AddVendor(),
//            popUpToRoute = Destination.Dashboard(),
//            isSingleTop = true,
//            inclusive = true
//        )
//    }

}