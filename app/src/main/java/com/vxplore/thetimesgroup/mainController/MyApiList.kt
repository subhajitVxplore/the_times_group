package com.vxplore.thetimesgroup.mainController

import com.vxplore.core.domain.model.BaseUrlModel
import retrofit2.http.GET

interface MyApiList {

    @GET("baseUrl")
    suspend fun getBaseUrl(): BaseUrlModel
}