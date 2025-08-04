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
    val transaccionId: Long?,
    val usuarioId: Long,
    val categoriaId: Long,
    val monto: Double,
    val fechaTransaccion: Date,
    val usarSugerencia: Boolean,
    val descripcion: String,
)
