package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json
import java.util.Date

data class UsuarioResponseDto(
    @Json(name = "usuarioId")
    val usuarioId: Long?,

    @Json(name = "nombre")
    val nombre: String?,

    @Json(name = "email")
    val email: String?,

    @Json(name = "fechaRegistro")
    val fechaRegistro: Date?,

    @Json(name = "fotoPath")
    val fotoPath: String?
)