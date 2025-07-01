package com.cashcontrol.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SugerenciaConDetalleEntity(
    @Embedded val sugerencia: SugerenciaEntity,
    @Relation(
        parentColumn = "sugerenciaId",
        entityColumn = "sugerenciaId"
    )
    val detalles: List<SugerenciaDetalleEntity>
)
