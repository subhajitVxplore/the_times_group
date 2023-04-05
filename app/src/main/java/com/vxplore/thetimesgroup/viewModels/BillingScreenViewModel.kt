package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Action
import com.vxplore.core.common.Destination
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.core.domain.model.SearchVendorModel
import com.vxplore.core.domain.useCasess.BillingScreenUseCases
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.screens.getPaperPrice
import com.vxplore.thetimesgroup.screens.getPersonAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor(private val appNavigator: AppNavigator,private val billingScreenUseCases : BillingScreenUseCases) : ViewModel() {

    var expand = mutableStateOf(false)  // Expand State
    var stroke = mutableStateOf(1)

    var cashPayment = mutableStateOf(0)
    var previousDue = mutableStateOf(1000)
    var currentDue = mutableStateOf(0)

    var toiReturn = mutableStateOf("")

    var takenPapers = MutableList<Pair<Int, Int>>(getPaperPrice().size) { Pair(0,0) }
    var takenPaperTotal = mutableStateOf(0)
    var coupons = MutableList<Pair<Int, Int>>(getPersonAge().size) { Pair(0,0) }
    var couponTotal = mutableStateOf(0)


    private val _suggestionsss: MutableStateFlow<List<SearchVendorModel>> = MutableStateFlow(emptyList())
    private val _suggestions = MutableStateFlow(emptyList<SearchVendor>())
    val suggestion = _suggestions.asStateFlow()
    var suggestionListVisibility by mutableStateOf(false)
    private var suggestionsBackup: List<SearchVendorModel> = emptyList()
    var searchVendorQuery by mutableStateOf("")

    val toastError = mutableStateOf("")

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

    fun clearVendorsQuery() {
        searchVendorQuery = ""
        suggestionListVisibility = false
        viewModelScope.launch {
            suggestionsBackup.apply {
                _suggestionsss.emit(this)
            }
        }
    }

    fun updateVendorsQuery(query: String) {
        searchVendorQuery = query
        getPrescriptionSuggestions(query = searchVendorQuery)
    }

    private fun getPrescriptionSuggestions(query: String) {
        billingScreenUseCases.generateVendorsSuggestions(query = query).onEach {
            when (it.actionType) {
                Action.SUGGESTIONS -> {
                    it.targetType?.castListToRequiredTypes<SearchVendor>()?.let {
                        _suggestions.emit(it)
                      //  Log.d("Hello==","Responssse${it.toString()}")
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