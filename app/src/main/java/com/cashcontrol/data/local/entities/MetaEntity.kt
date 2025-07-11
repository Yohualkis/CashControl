package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Metas",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class MetaEntity(
    @PrimaryKey
    val metaId: Long?,
    val usuarioId: Long?,
    val descripcion: String,
    val montoMeta: Double,
)
