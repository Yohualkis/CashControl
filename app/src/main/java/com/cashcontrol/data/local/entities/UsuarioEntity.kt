package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val usuarioId: Long?,
    val nombre: String,
    val email: String,
    val fotoPath: String?,
    val fechaRegistro: Date?
)
