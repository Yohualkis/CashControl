package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json

data class FiltroCategoriaRequestDto(
    @Json(name = "tipoCategoria")
    val tipo: String,

    @Json(name = "usuarioId")
    val usuarioId: Long
)
