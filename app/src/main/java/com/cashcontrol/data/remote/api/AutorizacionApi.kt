package com.cashcontrol.data.remote.api

import com.cashcontrol.data.remote.dto.AutorizacionResponse
import com.cashcontrol.data.remote.dto.LoginRequest
import com.cashcontrol.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AutorizacionApi {
    @POST("api/autorizacion/login")
    suspend fun login(@Body request: LoginRequest): Response<AutorizacionResponse>

    @POST("api/autorizacion/register")
    suspend fun register(@Body request: RegisterRequest): Response<AutorizacionResponse>
}