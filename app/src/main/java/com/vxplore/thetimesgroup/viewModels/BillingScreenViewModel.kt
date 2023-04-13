package com.vxplore.thetimesgroup.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Action
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.*
import com.vxplore.core.domain.useCasess.BillingScreenUseCases
import com.vxplore.thetimesgroup.custom_views.UiData
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.helpers_impl.SavableMutableState
import com.vxplore.thetimesgroup.screens.getPaperPrice
import com.vxplore.thetimesgroup.screens.getPersonAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val billingScreenUseCases: BillingScreenUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var expand = mutableStateOf(false)  // Expand State
    var stroke = mutableStateOf(1)

    var cashPayment = mutableStateOf(0)
    var previousDue = mutableStateOf(0)
    var currentDue = mutableStateOf(0)

    var toiReturn = mutableStateOf("")

    // var takenPapers = MutableList<Pair<Int, Int>>(getPaperPrice().size) { Pair(0, 0) }


    private val _paperss = MutableStateFlow(emptyList<Paper>())
    val paperss = _paperss.asStateFlow()
    private val _couponss = MutableStateFlow(emptyList<Coupon>())
    val couponss = _couponss.asStateFlow()

    var paperssListSize = mutableStateOf(0)

    // var takenPapers = MutableList<Pair<Int, Int>>(paperssListSize.value) { Pair(0, 0) }
    lateinit var takenPapers: MutableList<Pair<Int, Int>>
    var takenPaperTotal = mutableStateOf(0)
    var eachPaperTotal = mutableStateOf(0)
    var eachReturnPaperTotal = mutableStateOf(0)
    var returnPaperTotal = mutableStateOf(0)
    var takenMinusreturnPaperTotal = mutableStateOf(0)
    var coupons = MutableList<Pair<Int, Int>>(getPersonAge().size) { Pair(0, 0) }
    var couponTotal = mutableStateOf(0)

    private val _suggestionsss: MutableStateFlow<List<SearchVendorModel>> =
        MutableStateFlow(emptyList())
    private val _suggestions = MutableStateFlow(emptyList<SearchVendor>())
    val suggestion = _suggestions.asStateFlow()
    var suggestionListVisibility by mutableStateOf(false)
    private var suggestionsBackup: List<SearchVendorModel> = emptyList()
    var searchVendorQuery by mutableStateOf("")

    val toastError = mutableStateOf("")

    init {
        currentDue.value = previousDue.value
        getPapersByVendorId(" ")
    }

    fun calculateCurrentDue() {
        if ((takenPaperTotal.value != 0) or (cashPayment.value != 0) or (couponTotal.value != 0)) {
            currentDue.value += ((takenPaperTotal.value - cashPayment.value) - couponTotal.value)
        } else {

        }
    }

    fun calculateTakenPapersPrice() {
        //currentTakenPaperTotal.value=takenPaperTotal.value
        takenPaperTotal.value = 0
        takenPapers.forEach {
//            takenPaperTotal.value += it.first * it.second
            eachPaperTotal.value = it.first * it.second
            takenPaperTotal.value += eachPaperTotal.value
        }
        //eachPaperTotal.value=0
    }

    fun calculateReturnPapersPrice() {
        returnPaperTotal.value = 0
        takenPapers.forEach {
            eachReturnPaperTotal.value = it.first * it.second
            returnPaperTotal.value +=eachReturnPaperTotal.value
//            takenPaperTotal.value =takenPaperTotal.value - eachReturnPaperTotal.value
            //takenPaperTotal.value =takenPaperTotal.value.minus(eachReturnPaperTotal.value)
        }
    }


    fun calculateTakenMinusReturnPaperTotal(){
        takenMinusreturnPaperTotal.value=(takenPaperTotal.value - returnPaperTotal.value)
    }



    fun calculateCoupon() {
        coupons.forEach {
            couponTotal.value += it.first * it.second
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

    val circleLoading = SavableMutableState(
        key = UiData.PaperApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    fun getPapersByVendorId(vendor_id: String) {
        billingScreenUseCases.getPapersByVendorId(vendor_id)//original
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                circleLoading.setValue(it)
                            }
                        }
                    }
                    EmitType.PAPERS -> {
                        it.value?.castListToRequiredTypes<Paper>()?.let { papers ->
                            _paperss.update { papers }
                            takenPapers = MutableList(papers.size) { 0 to 0 }
                        }
                    }
                    EmitType.COUPONS -> {
                        it.value?.castListToRequiredTypes<Coupon>()?.let { coupon ->
                            _couponss.update { coupon }
                        }
                    }

                    EmitType.DUE -> {
                        it.value?.castValueToRequiredTypes<Int>()?.let {
                            previousDue.value = it
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
////////////////////////////////////////////////////////////////////////////

    fun clearVendorsQuery() {
        getPapersByVendorId(" ")
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
        getVendorsSuggestions(query = searchVendorQuery)
    }

    private fun getVendorsSuggestions(query: String) {
        billingScreenUseCases.generateVendorsSuggestions(query = query).onEach {
            when (it.actionType) {
                Action.SUGGESTIONS -> {
                    it.targetType?.castListToRequiredTypes<SearchVendor>()?.let {
                        _suggestions.emit(it)
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
                        toastError.value = it
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }


}