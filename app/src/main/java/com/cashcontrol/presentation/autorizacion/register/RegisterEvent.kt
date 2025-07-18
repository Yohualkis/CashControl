package com.cashcontrol.presentation.autorizacion.register

sealed interface RegisterEvent {
    data class NombreChange(val nombre: String): RegisterEvent
    data class EmailChange(val email: String): RegisterEvent
    data class PasswordChange(val password: String): RegisterEvent
    data class ConfirmPasswordChange(val password: String): RegisterEvent

    data object Register: RegisterEvent
    data object LimpiarErrores: RegisterEvent
    data object LimpiarClaveYErrorContrasena: RegisterEvent
}