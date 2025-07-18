package com.cashcontrol.data.remote

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.dto.CategoriaResponseDto
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaApi,
    private val autorizacionApi: AutorizacionApi
) {
    // CATEGORIAS


    // AUTORIZACION
    suspend fun login(loginRequest: AutorizacionRequestDto) = autorizacionApi.login(loginRequest)
    suspend fun register(usuarioRequestDto: UsuarioRequestDto) = autorizacionApi.register(usuarioRequestDto)
}