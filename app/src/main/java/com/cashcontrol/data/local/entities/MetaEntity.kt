package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Metas")
data class MetaEntity(
    @PrimaryKey
    val metaId: Int?,
    val usuarioId: Int?,
    val descripcion: String,
    val montoMeta: Double,
)
