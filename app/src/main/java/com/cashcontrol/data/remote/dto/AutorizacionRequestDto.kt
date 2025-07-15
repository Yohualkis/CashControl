package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json

data class AutorizacionRequestDto(
    @Json(name = "email")
    val email: String,

    @Json(name = "password")
    val contrasena: String
)
