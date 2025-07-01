package com.cashcontrol.data.local.entities

import androidx.room.Entity
import java.util.Date

@Entity(tableName = "Sugerencias")
data class SugerenciaEntity(
    val sugerenciaId: Int?,
    val usuarioId: Int?,
    val estaVisto: Boolean = false,
    val fechaCreacion: Date = Date()
)
