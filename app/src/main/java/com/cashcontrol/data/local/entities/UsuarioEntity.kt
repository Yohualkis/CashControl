package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val usuarioId: Int?,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val fechaRegistro: Date
)
