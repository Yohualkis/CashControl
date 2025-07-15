package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.AutorizacionResponseDto
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizacionApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: AutorizacionRequestDto): AutorizacionResponseDto

    @POST("api/usuarios/register")
    suspend fun register(@Body usuarioRequestDto: UsuarioRequestDto): UsuarioRequestDto
}