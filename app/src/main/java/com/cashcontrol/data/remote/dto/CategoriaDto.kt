package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json

data class CategoriaDto(
    @Json(name = "categoriaId")
    val categoriaId: Long?,

    @Json(name = "usuarioId")
    val usuarioId: Long?,

    @Json(name = "tipo")
    val tipo: String,

    @Json(name = "descripcion")
    val descripcion: String
)
