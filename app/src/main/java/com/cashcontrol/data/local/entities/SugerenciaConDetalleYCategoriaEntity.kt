package com.cashcontrol.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SugerenciaConDetalleYCategoriaEntity(
    @Embedded val sugerencia: SugerenciaEntity,

    @Relation(
        entity = SugerenciaDetalleEntity::class,
        parentColumn = "sugerenciaId",
        entityColumn = "sugerenciaId"
    )
    val detalles: List<SugerenciaDetalleConCategoriaEntity>
)
