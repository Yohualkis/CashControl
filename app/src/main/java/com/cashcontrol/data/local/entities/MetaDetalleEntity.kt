package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Metas_Detalles",
    foreignKeys = [
        ForeignKey(
            entity = MetaEntity::class,
            parentColumns = ["metaId"],
            childColumns = ["metaId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class MetaDetalleEntity(
    @PrimaryKey
    val metaDetalleId: Long = 0,
    val metaId: Long,
    val montoAporte: Double,
    val fechaAporte: Date = Date()
)
