package com.vxplore.thetimesgroup.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.AppNavigator
import com.vxplore.core.common.Action
import com.vxplore.core.common.Destination
import com.vxplore.core.common.EmitType
import com.vxplore.core.domain.model.*
import com.vxplore.core.domain.useCasess.BillingScreenUseCases
import com.vxplore.core.helpers.AppStore
import com.vxplore.thetimesgroup.custom_views.UiData
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import com.vxplore.thetimesgroup.helpers_impl.SavableMutableState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val billingScreenUseCases: BillingScreenUseCases,
    private val pref: AppStore,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //val mainActivity:MainActivity = TODO()
    //val mainActivity = mutableStateOf<MainActivity>()
    var expand = mutableStateOf(false)  // Expand State
    var stroke = mutableStateOf(1)

    var cashPayment = mutableStateOf(0)
    var previousDue = mutableStateOf(0)
    var currentDue = mutableStateOf(0)
    var isAddedBillData = mutableStateOf(false)
    var pdfData = mutableStateOf("")

    var generateBillButtonText:String="Generate Bill"
    // var takenPapers = MutableList<Pair<Int, Int>>(getPaperPrice().size) { Pair(0, 0) }

    private val _paperss = MutableStateFlow(emptyList<Paper>())
    val paperss = _paperss.asStateFlow()
    private val _couponss = MutableStateFlow(emptyList<Coupon>())
    val couponss = _couponss.asStateFlow()

    val takenPapers = MutableStateFlow(mutableListOf<SendTodayPapers>())
    val takenPapersJason = MutableStateFlow(mutableListOf<SendTodayPapers>())
    val returnPapers = MutableStateFlow(mutableListOf<SendReturnPapers>())
    val returnPapersJason = MutableStateFlow(mutableListOf<SendReturnPapers>())
    var takenMinusreturnPaperTotal = mutableStateOf(0)
    val coupons = MutableStateFlow(mutableListOf<Coupon>())
    val couponsJason = MutableStateFlow(mutableListOf<Coupon>())
    var cashMinusCouponTotal = mutableStateOf(0)

    private val _suggestionsss: MutableStateFlow<List<SearchVendorModel>> =
        MutableStateFlow(emptyList())
    private val _suggestions = MutableStateFlow(emptyList<SearchVendor>())
    val suggestion = _suggestions.asStateFlow()
    var suggestionListVisibility by mutableStateOf(false)
    private var suggestionsBackup: List<SearchVendorModel> = emptyList()
    var searchVendorQuery by mutableStateOf("")

    val toastError = mutableStateOf("")

    var vendorId by mutableStateOf("")

    // lateinit var takenPapersKey: List<SendTodayPapers>
    //val takenPapersKey = MutableStateFlow(mutableListOf<SendTodayPapers>())
    var takenPapersKey: List<SendTodayPapers> = emptyList()
    var returnPapersKey: List<SendReturnPapers> = emptyList()

    init {
        currentDue.value = previousDue.value
        // getPapersByVendorId(" ")
    }

    val takenPapersTotal = mutableStateOf(0)
    val returnsTotal = mutableStateOf(0)
    val couponsTotal = mutableStateOf(0)

    fun calculateTakenPapersPrice(value1: Int, value2: Int, index: Int) {
        takenPapers.update { values ->
            values[index].value = value1 * value2
            takenPapersTotal.value = values.sumOf { it.value }
            values
        }
        takenPapersJason.update { values ->
            values[index].value =value2
            values
        }


    }

    fun calculateReturnPapersPrice(value1: Int, value2: Int, index: Int) {
        returnPapers.update { values ->
//            values[index] = value1 * value2
//            returnsTotal.value = values.sum()
            values[index].value = value1 * value2
            returnsTotal.value = values.sumOf { it.value }
            values
        }
        returnPapersJason.update { values ->
            values[index].value = value2
            values
        }


    }

    fun calculateCouponPrice(value1: Int, value2: Int, index: Int) {
        //couponsJason.value=coupons.value
        coupons.update { values ->
            values[index] = values[index].copy(value = value1 * value2)
            couponsTotal.value = values.sumOf { it.value }
            values
        }
        couponsJason.update { values ->
            values[index] = values[index].copy(value =value2)
            values
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
    fun onBillingToBillPreview() {
        appNavigator.tryNavigateTo(
            route = Destination.BillPreview(),
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

                            takenPapers.update {
                                MutableList(papers.size) {
                                    SendTodayPapers(key = papers[it].key, value = 0)
                                }
                            }
                            takenPapersJason.update {
                                MutableList(papers.size) {
                                    SendTodayPapers(key = papers[it].key, value = 0)
                                }
                            }

                            returnPapers.update {
                                //MutableList(papers.size) { 0 }
                                MutableList(papers.size) {
                                    SendReturnPapers(key = papers[it].key, value = 0)
                                }
                            }
                            returnPapersJason.update {
                                MutableList(papers.size) {
                                    SendReturnPapers(key = papers[it].key, value = 0)
                                }
                            }

                        }
                    }
                    EmitType.COUPONS -> {
                        it.value?.castListToRequiredTypes<Coupon>()?.let { coupon ->
                            _couponss.update { coupon }
                            coupons.update {
                                MutableList(coupon.size) { idx ->
                                    Coupon(key = coupon[idx].key, value = 0)
                                }
                            }
                            couponsJason.update {
                                MutableList(coupon.size) { idx ->
                                    Coupon(key = coupon[idx].key, value = 0)
                                }
                            }


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
        // getPapersByVendorId(" ")
        _paperss.update { emptyList() }
        _couponss.update { emptyList() }
        searchVendorQuery = ""
        takenMinusreturnPaperTotal.value = 0
        cashPayment.value = 0
        couponsTotal.value = 0
        cashMinusCouponTotal.value = 0
        currentDue.value = 0
        pdfData.value=""
        expand.value = false
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



    fun generateBillByJson() {

//        val rawJsonData = GenerateBillDataRequestModel(
//            vendor_id = vendorId,
//            calculated_price = takenMinusreturnPaperTotal.value,
//            payment_by_cash = cashPayment.value,
//            due_amount = currentDue.value,
//            coupons = coupons.value.toList(),//as same class name in both two model classes(PapersByVendorIdModel & GenerateBillDataRequestModel)
//            today_papers = takenPapers.value.toList(),
//            return_papers = returnPapers.value.toList()
//        )
      //  Toast.makeText(context, "${takenMinusreturnPaperTotal.value} \n ${cashPayment.value} \n ${currentDue.value}\n ${couponsJason.value.toList()}",Toast.LENGTH_SHORT).show()

        val rawJsonData = GenerateBillDataRequestModel(
            vendor_id = vendorId,
            calculated_price = takenMinusreturnPaperTotal.value,
            payment_by_cash = cashPayment.value,
            due_amount = currentDue.value,
            coupons = couponsJason.value.filter { it.value != 0 },//as same class name in both two model classes(PapersByVendorIdModel & GenerateBillDataRequestModel)
            today_papers = takenPapersJason.value.filter { it.value != 0 },
            return_papers = returnPapersJason.value.filter { it.value != 0 }
        )



        billingScreenUseCases.generateBillByJson(rawJsonData)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {

                    EmitType.IS_ADDED -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let {
                            it
                            isAddedBillData.value = it
                        }
                    }
                    EmitType.PDF_URL -> {
                        it.value?.castValueToRequiredTypes<String>()?.let {
                            pdfData.value = it
//                            pref.storePdfUrl(it)
//                            pdfUrl.value=pref.fetchPdfUrl()
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

}