package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.dao.UsuarioDao
import com.cashcontrol.data.local.entities.UsuarioEntity
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {
    suspend fun saveUser(user: UsuarioEntity) = usuarioDao.saveUser(user)
    suspend fun deleteUser() = usuarioDao.deleteUser()
    suspend fun getUser() = usuarioDao.getUser()
}