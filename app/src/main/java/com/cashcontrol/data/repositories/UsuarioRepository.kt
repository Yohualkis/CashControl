package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.dao.UsuarioDao
import com.cashcontrol.data.local.entities.UsuarioEntity
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {
    suspend fun save(user: UsuarioEntity) = usuarioDao.save(user)
    suspend fun getUsuarioById(id: Long?) = usuarioDao.getUsuarioById(id)
    suspend fun getUsuarioByEmail(email: String) = usuarioDao.getUsuarioByEmail(email)
}