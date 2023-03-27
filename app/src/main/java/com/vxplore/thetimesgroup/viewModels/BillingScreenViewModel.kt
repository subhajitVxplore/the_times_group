package com.vxplore.thetimesgroup.viewModels

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Destination
import com.vxplore.core.domain.model.Vendor
import com.vxplore.thetimesgroup.screens.Person
import com.vxplore.thetimesgroup.screens.getPaperPrice
import com.vxplore.thetimesgroup.screens.getPersonAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {

    fun search(updatedAddress: String) {

    }

    var showClearButton = mutableStateOf(false)
    var expand = mutableStateOf(false)  // Expand State
    var stroke = mutableStateOf(1)

    var cashPayment = mutableStateOf(0)
    var previousDue = mutableStateOf(1000)
    var currentDue = mutableStateOf(0)

    var toiReturn = mutableStateOf("")
    var etReturn = mutableStateOf("")
    var esReturn = mutableStateOf("")

    //var personList: List<Person>
   // var couponList = listOf<Person>()
    //var coupons = MutableList(getPersonAge().size) { 0 }

    var takenPapers = MutableList<Pair<Int, Int>>(getPaperPrice().size) { Pair(0,0) }
    var takenPaperTotal = mutableStateOf(0)
    var coupons = MutableList<Pair<Int, Int>>(getPersonAge().size) { Pair(0,0) }
    var couponTotal = mutableStateOf(0)

    //val _coupons = coupons.toMutableStateList()


    init {
        currentDue.value=previousDue.value
    }
    fun calculateCurrentDue(){
        if((takenPaperTotal.value != 0) or(cashPayment.value != 0) or (couponTotal.value != 0)){

            currentDue.value+=((takenPaperTotal.value - cashPayment.value ) - couponTotal.value )
        }else{

        }
    }
    fun calculatePapersPrice(){
        takenPapers.forEach{
            takenPaperTotal.value += it.first*it.second
        }
    }
    fun calculateCoupon(){
        coupons.forEach{
            couponTotal.value += it.first*it.second
        }
    }

    fun onBillingToAddVendor() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendor(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }



}