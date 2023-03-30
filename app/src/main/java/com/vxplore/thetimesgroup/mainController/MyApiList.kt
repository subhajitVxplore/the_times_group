package com.vxplore.thetimesgroup.mainController

import com.vxplore.core.domain.model.*
import retrofit2.http.*

interface MyApiList {

    @GET("baseUrl")
    suspend fun getBaseUrl(): BaseUrlModel

    @GET("appVersion")
    suspend fun getAppVersion(): AppVersionModel

    @GET("Location/states")
    suspend fun getAllState(): AllStatesModel

    @GET("state/districts")
    suspend fun getDistrictByState(@Query("state") state: String): DistrictByStateModel

    @FormUrlEncoded
    @POST("users/{userId}/add/vendor")
    suspend fun register(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("address") address: String,
        @Field("state") state: String,
        @Field("district") district: String,
        @Field("pincode") pincode: String
    ): AddVendorModel



    @GET("Onboarding/sendOtp")
    suspend fun sendOtp(@Query("mobile") mobile: String): SendOtpModel

    @GET("Onboarding/verifyOtp")
    suspend fun verifyOtp(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String
    ): VerifyOtpModel

    @FormUrlEncoded
    @POST("users/UABEC3A5F20230328/add/vendor")
    suspend fun addVendor(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("pincodes") pincodes: String
    ): AddVendorModel

//    @FormUrlEncoded
//    @POST("api/v2/app/changeLanguageApi")
//    suspend fun addVendor(hashMap:HashMap<String, String>): AddVendorModel




}