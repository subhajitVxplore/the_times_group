package com.vxplore.thetimesgroup.data.online

import com.vxplore.core.domain.model.AppVersionResponse
import retrofit2.http.GET

interface AppVersionApi {


    @GET("dbc734947dc2b6deb690")
    suspend fun getAppVersion(): AppVersionResponse


}