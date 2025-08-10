package com.cashcontrol.presentation.autorizacion.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cashcontrol.R
import com.cashcontrol.presentation.composables.MensajeDeErrorGenerico
import com.cashcontrol.presentation.composables.TextfieldGenerico
import com.cashcontrol.presentation.composables.TextfieldPassword

@Composable
fun RegisterScreen(
    viewmodel: RegisterViewModel = hiltViewModel(),
    goToLogin: () -> Unit,
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    RegisterScreenView(
        uiState = uiState,
        onEvent = viewmodel::onEvent,
        goToLogin = goToLogin,
    )
}

@Composable
fun RegisterScreenView(
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    goToLogin: () -> Unit,
) {
    var showPass by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF016FCE)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.cashcontrol_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(120.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.surfaceDim
                )
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(R.string.subtitulo_register),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre
                TextfieldGenerico(
                    value = uiState.nombre ?: "",
                    onValueChange = { onEvent(RegisterEvent.NombreChange(it)) },
                    labelResource = R.string.placeholder_nombre_register,
                    icono = Icons.Default.Person,
                    errorMessagePass = uiState.errorNombre
                )
                Box(Modifier.fillMaxWidth()) {
                    MensajeDeErrorGenerico(uiState.errorNombre)
                }

                // Email
                TextfieldGenerico(
                    value = uiState.email ?: "",
                    onValueChange = { onEvent(RegisterEvent.EmailChange(it)) },
                    labelResource = R.string.placeholder_email_register,
                    icono = Icons.Default.Email,
                    errorMessagePass = uiState.errorEmail
                )
                Box(Modifier.fillMaxWidth()) {
                    MensajeDeErrorGenerico(uiState.errorEmail)
                }

                // Password
                TextfieldPassword(
                    value = uiState.password ?: "",
                    onValueChange = { onEvent(RegisterEvent.PasswordChange(it)) },
                    labelResource = R.string.placeholder_contraseña,
                    icono = Icons.Default.Lock,
                    showPass = showPass,
                    onTogglePasswordVisibility = { showPass = !showPass },
                    errorMessagePass = uiState.errorPassword
                )
                Box(Modifier.fillMaxWidth()) {
                    MensajeDeErrorGenerico(uiState.errorPassword)
                }

                // Confirmar Password
                TextfieldPassword(
                    value = uiState.confirmPassword ?: "",
                    onValueChange = { onEvent(RegisterEvent.ConfirmPasswordChange(it)) },
                    labelResource = R.string.placeholder_confirmar_contraseña_register,
                    icono = Icons.Default.Lock,
                    showPass = showPass,
                    onTogglePasswordVisibility = { showPass = !showPass },
                    errorMessagePass = uiState.errorConfirmPassword
                )
                Box(Modifier.fillMaxWidth()) {
                    MensajeDeErrorGenerico(uiState.errorConfirmPassword)
                }

                // Botón Continuar
                // Botón Continuar
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(RegisterEvent.Register)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = MaterialTheme.shapes.small,
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    } else
                        Text(text = stringResource(R.string.btn_continuar))
                }
                MensajeDeErrorGenerico(uiState.errorGeneral)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.volver_login),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            focusManager.clearFocus()
                            goToLogin()
                        },
                    color = MaterialTheme.colorScheme.tertiary,
                )
                // Footer
                Text(
                    text = stringResource(R.string.copyright),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    RegisterScreenView(
        uiState = RegisterUiState(),
        onEvent = {},
        goToLogin = {},
    )
}
