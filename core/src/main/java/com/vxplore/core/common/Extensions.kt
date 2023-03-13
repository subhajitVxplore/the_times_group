package com.vxplore.core.common

import kotlinx.coroutines.flow.FlowCollector


suspend inline fun <reified R> FlowCollector<Data>.handleFailedResponse(
    response: Resource<R>,
    message: String?,
    emitType: EmitType
) {
    when (message != null) {
        true -> {
            emit(Data(emitType, message))
        }
        else -> {
            emit(Data(emitType, response.message))
        }
    }
}