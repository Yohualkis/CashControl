package com.cashcontrol.presentation.autorizacion.login

import com.cashcontrol.data.local.entities.UsuarioEntity

data class LoginUiState(
    val email: String? = "",
    val contrasena: String? = "",
    val emailErrorMessage: String? = "",
    val contrasenaErrorMessage: String? = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorGeneral: String? = null,
    val usuario: UsuarioEntity? = null
)
