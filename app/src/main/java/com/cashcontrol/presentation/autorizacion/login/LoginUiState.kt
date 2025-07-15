package com.cashcontrol.presentation.autorizacion.login

import com.cashcontrol.data.local.entities.UsuarioEntity

data class LoginUiState(
    val email: String? = null,
    val contrasena: String? = null,
    val emailErrorMessage: String?  = null,
    val contrasenaErrorMessage: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorGeneral: String? = null,
    val usuario: UsuarioEntity? = null
)
