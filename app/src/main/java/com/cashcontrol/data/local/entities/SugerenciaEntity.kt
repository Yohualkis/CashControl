package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Sugerencias",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class SugerenciaEntity(
    @PrimaryKey
    val sugerenciaId: Long?,
    val usuarioId: Long?,
    val estaVisto: Boolean = false,
    val fechaCreacion: Date = Date()
)
