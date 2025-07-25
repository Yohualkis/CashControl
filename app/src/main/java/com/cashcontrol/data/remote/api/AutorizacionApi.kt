package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import com.cashcontrol.data.remote.dto.UsuarioResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizacionApi {
    @POST("api/usuarios/login")
    suspend fun login(@Body request: AutorizacionRequestDto): UsuarioResponseDto

    @POST("api/usuarios/register")
    suspend fun register(@Body usuarioRequestDto: UsuarioRequestDto): UsuarioResponseDto
}