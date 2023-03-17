package com.vxplore.core.domain.model

data class VendorDetailsResponse(
    val message: String,
    val status: Boolean,
    val vendor_list: List<Vendor>
)

data class Vendor(
    val daily_avg: String,
    val payment_due: String,
    val return_avg: String,
    val top_vendors: String
)