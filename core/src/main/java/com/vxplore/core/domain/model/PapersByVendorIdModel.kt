package com.vxplore.core.domain.model

data class PapersByVendorIdModel(
    val coupons: List<Coupon>,
    val message: String,
    val papers: List<Paper>,
    val status: Boolean
)


data class Paper(
    val key: String,
    val previous_paper_count: Int,
    val price: Int,
    val value: String
)

data class Coupon(
    val key: String,
    val value: Int
)