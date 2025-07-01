package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Transacciones",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["categoriaId"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class TransaccionEntity(
    @PrimaryKey
    val transaccionId: Int?,
    val usuarioId: Int?,
    val categoriaId: Int?,
    val tipoTransaccion: String,
    val monto: Double,
    val fechaTransaccion: Date?,
    val usarSugerencia: Boolean,
    val descripcion: String,
    val updatedAt: Long, // timestamp Unix ms de ultima actualizacion
    val isDirty: Boolean = false // true si esta modificado localmente y no sincronizado
)
