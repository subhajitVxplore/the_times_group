package com.vxplore.core.domain.model

data class GenerateBillDataRequestModel(
    val vendor_id: String,
    val calculated_price: Int,
    val payment_by_cash: Int,
    val due_amount: Int,
    val coupons: List<SendCoupons>,
    val today_papers: List<SendTodayPapers>,
    val return_papers: List<SendReturnPapers>,

)
data class SendCoupons(
    val key: String,
    val value: Int,
)

data class SendTodayPapers(
    val key: String,
    val value: Int,
)
data class SendReturnPapers(
    val key: String,
    val value: Int,
)