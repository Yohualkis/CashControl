package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Metas_Detalles")
data class MetaDetalleEntity(
    @PrimaryKey
    val metaDetalleId: Long?,
    val metaId: Long?,
    val montoAporte: Double,
    val fechaAporte: Date = Date()
)
