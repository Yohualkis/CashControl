package com.cashcontrol.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MetaConDetalleEntity(
    @Embedded val meta: MetaEntity,
    @Relation(
        parentColumn = "metaId",
        entityColumn = "metaId"
    )
    val detalles: List<MetaDetalleEntity>
)
