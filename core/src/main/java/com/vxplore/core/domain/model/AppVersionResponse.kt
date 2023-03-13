package com.vxplore.core.domain.model


data class AppVersionResponse(
    val status: Boolean,
    val message: String,
    val appVersion: AppVersion
)