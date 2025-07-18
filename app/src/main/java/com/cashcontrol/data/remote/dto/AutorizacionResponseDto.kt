package com.cashcontrol.data.remote.dto

import com.cashcontrol.data.local.entities.UsuarioEntity
import com.squareup.moshi.Json

data class AutorizacionResponseDto(
    @Json(name = "accessToken")
    val token: String,
    @Json(name = "usuario")
    val usuario: UsuarioResponseDto
)
