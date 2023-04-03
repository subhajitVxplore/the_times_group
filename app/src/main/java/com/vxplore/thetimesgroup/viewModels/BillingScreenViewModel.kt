package com.vxplore.thetimesgroup.viewModels

import android.app.Notification
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Action
import com.vxplore.core.common.Destination
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.core.domain.model.SearchVendorModel
import com.vxplore.core.domain.model.SortParameter
import com.vxplore.core.domain.model.Vendor
import com.vxplore.core.domain.useCasess.AddVendorUseCases
import com.vxplore.core.domain.useCasess.SearchVendorUseCases
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.screens.Person
import com.vxplore.thetimesgroup.screens.getPaperPrice
import com.vxplore.thetimesgroup.screens.getPersonAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor(private val appNavigator: AppNavigator,private val searchVendorUseCases : SearchVendorUseCases) : ViewModel() {


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
    private val _prescriptions: MutableStateFlow<List<SearchVendorModel>> = MutableStateFlow(emptyList())
    val prescriptions: StateFlow<List<SearchVendorModel>> = _prescriptions
//    private val _suggestions = MutableStateFlow<List<SearchVendor>>(emptyList())

    private val _suggestions = MutableStateFlow(emptyList<SearchVendor>())
    val suggestion = _suggestions.asStateFlow()
   // val suggestion: StateFlow<List<SearchVendor>> = _suggestions

    var suggestionListVisibility by mutableStateOf(false)
    private var prescriptionsBackup: List<SearchVendorModel> = emptyList()

    var prescriptionSortParameters = mutableStateListOf<SortParameter>()
    var prescriptionQuery by mutableStateOf("")


    val toastError = mutableStateOf("")


    var expandDropDown by mutableStateOf(false)
    fun openDropDown() {
        expandDropDown = !expandDropDown
    }
//    var prescriptionSortParameters = mutableStateListOf<SortParameter>()
//    var prescriptionQuery by mutableStateOf("")
//    var netWorkListener: ((Net) -> Unit) = {}



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
////////////////////////////////////////////////////////////////////////////

    fun clearPrescriptionQuery() {
        prescriptionQuery = ""
        suggestionListVisibility = false
        viewModelScope.launch {
            prescriptionsBackup.apply {
                _prescriptions.emit(this)
            }
        }
    }

    fun updatePrescriptionQuery(query: String) {
        prescriptionQuery = query
        getPrescriptionSuggestions(query = prescriptionQuery)
    }


    private fun getPrescriptionSuggestions(query: String) {
        searchVendorUseCases.generateVendorsSuggestions(query = query).onEach {
            when (it.actionType) {
                Action.SUGGESTIONS -> {
                    it.targetType?.castListToRequiredTypes<SearchVendor>()?.let {
                        _suggestions.emit(it)
                        Log.d("Hello==","Responssse${it.toString()}")
                    }
                }
                Action.NO_SUGGESTIONS -> {
                    it.targetType?.let {
                        val bool = it as? Boolean
                        bool?.apply {
                            suggestionListVisibility = this
                        }
                    }
                }
                Action.BACKEND_ERROR -> {
                    it.targetType?.castValueToRequiredTypes<String>()?.let {
                        toastError.value=it
                    }
                }

                else -> {}
            }

        }.launchIn(viewModelScope)
    }





}