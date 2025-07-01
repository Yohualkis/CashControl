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
    val sugerenciaId: Int?,
    val usuarioId: Int?,
    val estaVisto: Boolean = false,
    val fechaCreacion: Date = Date()
)
