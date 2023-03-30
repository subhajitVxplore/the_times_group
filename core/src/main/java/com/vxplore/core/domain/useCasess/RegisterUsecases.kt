package com.vxplore.core.domain.useCasess

import com.vxplore.core.common.*
import com.vxplore.core.domain.repositoriess.MobileNoScreenRepository
import com.vxplore.core.domain.repositoriess.RegisterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUsecases @Inject constructor(private val registerRepository: RegisterRepository) {

    fun getAllState() = flow {
        emit(Data(EmitType.Loading, true))
        when (val response = registerRepository.getAllStateRepository()) {
            is Resource.Success -> {
                emit(Data(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(Data(EmitType.States, value = states))
                            emit(Data(type = EmitType.INFORM, value = message))
                        }
                        else -> {
                            emit(Data(type = EmitType.BackendError, value = message))
                        }
                    }
                }
            }
            is Resource.Error -> {
                handleFailedResponse(
                    response = response,
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }
            else -> {

            }
        }
    }


    fun getDistrictByStateState(state: String) = flow {
        emit(Data(EmitType.Loading, true))
        when (val response = registerRepository.getDistrictByStateRepository(state)) {
            is Resource.Success -> {
                emit(Data(EmitType.Loading, false))
                response.data?.apply {
                    when (status) {
                        true -> {
                            emit(Data(EmitType.Districts, value = districts))
                            emit(Data(type = EmitType.INFORM, value = message))
                        }
                        else -> {
                            emit(Data(type = EmitType.BackendError, value = message))
                        }
                    }
                }
            }
            is Resource.Error -> {
                handleFailedResponse(
                    response = response,
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }
            else -> {

            }
        }
    }



}