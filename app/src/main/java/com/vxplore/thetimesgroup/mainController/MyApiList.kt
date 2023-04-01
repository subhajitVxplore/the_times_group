package com.vxplore.thetimesgroup.mainController

import com.vxplore.core.domain.model.*
import retrofit2.http.*

interface MyApiList {

    @GET("baseUrl")
    suspend fun getBaseUrl(): BaseUrlModel

    @GET("appVersion")
    suspend fun getAppVersion(): AppVersionModel

    @GET("states")
    suspend fun getAllState(): AllStatesModel

    @GET("state/districts")
    suspend fun getDistrictByState(@Query("state") state: String): DistrictByStateModel

    @GET("state/districts/pincodes")
    suspend fun getPincodeByDistrict(@Query("district") district: String): PincodeByDistrict

    @FormUrlEncoded
    @POST("users/{userId}/registration")
    suspend fun register(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("address") address: String,
        @Field("state") state: String,
        @Field("district") district: String,
        @Field("pincode") pincode: String
    ): RegisterModel

    @GET("sendOtp")
    suspend fun sendOtp(@Query("mobile") mobile: String): SendOtpModel

    @GET("verifyOtp")
    suspend fun verifyOtp(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String
    ): VerifyOtpModel

    @FormUrlEncoded
    @POST("users/{userId}/add/vendor")
    suspend fun addVendor(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("pincodes") pincodes: String
    ): AddVendorModel

    @GET("vendor/search")
    suspend fun searchVendor(
        @Query("distributor_id") distributor_id: String,
        @Query("search_text") search_text: String
    ): SearchVendorModel

//    @FormUrlEncoded
//    @POST("api/v2/app/changeLanguageApi")
//    suspend fun addVendor(hashMap:HashMap<String, String>): AddVendorModel

}