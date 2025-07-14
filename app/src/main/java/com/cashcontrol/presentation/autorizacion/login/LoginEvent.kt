package com.cashcontrol.presentation.autorizacion.login

sealed interface LoginEvent {
    data class EmailChange(val email: String): LoginEvent
    data class ContrasenaChange(val contrasena: String): LoginEvent

    data object Login: LoginEvent
    data object Logout: LoginEvent
    data object CheckSesionActual : LoginEvent
    data object LimpiarErrores: LoginEvent
    data object LimpiarClaveYErrorContrasena: LoginEvent
}