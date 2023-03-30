package com.vxplore.core.domain.model

import com.vxplore.core.common.DropDownCommonInterface

data class PincodeByDistrict(
    val message: String,
    val pincodes: List<Pincode>,
    val status: Boolean
)

data class Pincode(
    val id: Int,
    override val value: String
) : DropDownCommonInterface