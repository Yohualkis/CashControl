package com.cashcontrol.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SugerenciaDetalleConCategoriaEntity(
    @Embedded val detalle: SugerenciaDetalleEntity,

    @Relation(
        parentColumn = "categoriaId",
        entityColumn = "categoriaId"
    )
    val categoria: CategoriaEntity?
)
