package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Sugerencias_Detalles")
data class SugerenciaDetalleEntity(
    @PrimaryKey
    val sugDetalleId: Int?,
    val sugerenciaId: Int?,
    val categoriaId: Int?,
    val descripcion: String,
    val tipoOperacion: String,
    val montoOperacion: Double,
    val fechaOperacion: Date
)
