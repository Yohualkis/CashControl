package com.cashcontrol.presentation.autorizacion.register

import com.cashcontrol.data.remote.dto.UsuarioRequestDto

data class RegisterUiState(
    val nombre: String? = "",
    val email: String? = "",
    val password: String? = "",
    val confirmPassword: String? = "",

    val errorNombre: String? = "",
    val errorEmail: String? = "",
    val errorPassword: String? = "",
    val errorConfirmPassword: String? = "",
    val errorGeneral: String? = "",

    val isLoading: Boolean = false,
    val usuario: UsuarioRequestDto? = null,
)
