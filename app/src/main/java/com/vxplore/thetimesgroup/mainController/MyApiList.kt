package com.vxplore.thetimesgroup.mainController

import com.vxplore.core.domain.model.BaseUrlModel
import com.vxplore.core.domain.model.SendOtpModel
import com.vxplore.core.domain.model.VerifyOtpModel
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiList {

    @GET("baseUrl")
    suspend fun getBaseUrl(): BaseUrlModel

    @GET("Onboarding/sendOtp")
    suspend fun sendOtp(@Query("mobile") mobile: String): SendOtpModel

    @GET("Onboarding/verifyOtp")
    suspend fun verifyOtp(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String
    ): VerifyOtpModel


}