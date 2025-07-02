package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json

data class LoginRequestDto(
    @Json(name = "email")
    val email: String,

    @Json(name = "contrasena")
    val contrasena: String
)
