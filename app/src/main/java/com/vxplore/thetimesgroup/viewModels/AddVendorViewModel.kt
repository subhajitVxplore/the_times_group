package com.vxplore.thetimesgroup.viewModels

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import com.vxplore.core.domain.model.Pincode
import com.vxplore.core.domain.model.Pincodes
import com.vxplore.core.domain.model.SearchVendor
import com.vxplore.core.domain.model.SearchVendorModel
import com.vxplore.core.domain.useCasess.AddVendorUseCases
import com.vxplore.core.domain.useCasess.RegisterUsecases
import com.vxplore.thetimesgroup.extensions.castListToRequiredTypes
import com.vxplore.thetimesgroup.extensions.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class AddVendorViewModel @Inject constructor(
    private val addVendorUseCases: AddVendorUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var yourNameText = mutableStateOf("")
    var mobileText = mutableStateOf("")
    var emailAddressText = mutableStateOf("")
    var addressText = mutableStateOf("")

    private val _pincodes = MutableStateFlow(emptyList<Pincodes>())
    val pincodes = _pincodes.asStateFlow()
    var selectedPincode = mutableStateOf("")

    private val _suggestionsss: MutableStateFlow<List<Pincodes>> = MutableStateFlow(emptyList())
    var suggestionListVisibility by mutableStateOf(false)
    private var suggestionsBackup: List<Pincodes> = emptyList()
    var filteredPincodes = mutableListOf<Pincodes>()

    var currentString = mutableStateOf("")
    val isFocused = mutableStateOf(false)
    val selectedPincodes = mutableListOf<Pincodes>()
    // var filteredPincodes = StateFlow(emptyList<Pincodes>())

//    private val _suggestionsss: MutableStateFlow<List<SearchVendorModel>> = MutableStateFlow(emptyList())
//    private val _suggestions = MutableStateFlow(emptyList<SearchVendor>())
//    val suggestion = _suggestions.asStateFlow()
//    var suggestionListVisibility by mutableStateOf(false)
//    private var suggestionsBackup: List<SearchVendorModel> = emptyList()
//    var searchVendorQuery by mutableStateOf("")
//    val toastError = mutableStateOf("")

    init {
        //getPincodesByDistributorId("")
    }

    fun onAddVendorToAddVendorSuccess() {
        appNavigator.tryNavigateTo(
            route = Destination.AddVendorSuccess(),
            // popUpToRoute = Destination.Dashboard(),
            //isSingleTop = true,
            //inclusive = true
        )
    }


    fun addVendor() {
        addVendorUseCases.addVendor(
            yourNameText.value, mobileText.value, emailAddressText.value, selectedPincode.value
        ).flowOn(Dispatchers.IO).onEach {
                when (it.type) {
                    EmitType.Navigate -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let { destination ->
                                appNavigator.tryNavigateTo(
                                    destination,
                                    popUpToRoute = Destination.Register.fullRoute,
                                    isSingleTop = true,
                                    inclusive = true
                                )
                            }
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
            }.launchIn(viewModelScope)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    fun clearPincodesQuery() {
        selectedPincode.value = ""
        suggestionListVisibility = false
        viewModelScope.launch {
            suggestionsBackup.apply {
                _suggestionsss.emit(this)
            }
        }
    }

    fun updatePincodesQuery(query: String) {
        selectedPincode.value = query
        getPincodesByDistributorId(selectedPincode.value)
//        viewModelScope.launch {
////            _pincodes.emit(pincodes.value.filterList {
////                this.pincode.matches(query.toRegex())
////            })
//            pincodes.value.filter {
//                it.pincode.startsWith(selectedPincode.value)
//            }.take(3)
//        }
    }

    fun getPincodesByDistributorId(query: String) {


        addVendorUseCases.getPincodesByDistributorId(query).flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {
                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                // pinCodeLoading.setValue(it)
                            }
                        }
                    }
                    EmitType.Pincodes -> {
                        it.value?.castListToRequiredTypes<Pincodes>()?.let { pincodes ->
                            _pincodes.update { pincodes }
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
            }.launchIn(viewModelScope)
    }


    fun getValueWithComma() {
        if (selectedPincode.value.length.equals(6)) {
            // selectedPincode.value+","
            selectedPincode.value.plus(",")

        } else {
            selectedPincode.value
        }
    }

    fun filterSuggestions() {
        val searchedText = selectedPincode.value
        filteredPincodes = if (searchedText.isEmpty()) {
            pincodes.value.toMutableList()
        } else {
            val resultList = ArrayList<Pincodes>()
            for (p in pincodes.value) {
                resultList.add(p)
            }
            resultList
        }
    }

    fun onSelectPincode(pincodes: Pincodes) {
        selectedPincode.value = "${selectedPincode.value},${pincodes.pincode},"
    }
}