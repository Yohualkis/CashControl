package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Sugerencias_Detalles")
data class SugerenciaDetalleEntity(
    @PrimaryKey
    val sugDetalleId: Long?,
    val sugerenciaId: Long?,
    val categoriaId: Long?,
    val descripcion: String,
    val tipoOperacion: String,
    val montoOperacion: Double,
    val fechaOperacion: Date
)
