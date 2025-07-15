package com.cashcontrol.presentation.autorizacion.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun LoginScreen(
    viewmodel: LoginViewmodel = hiltViewModel(),
    goToDash: () -> Unit,
    goToRegister: () -> Unit,
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            goToDash()
        }
    }

    BackHandler(enabled = uiState.isLoggedIn) {
        goToDash()
    }

    LoginScreenView(
        uiState = uiState,
        onEvent = viewmodel::onEvent,
        goToRegister = goToRegister,
    )
}

@Composable
fun LoginScreenView(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    goToRegister: () -> Unit,
) {
    var showPass by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() },
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
                ),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(R.string.subtitulo_login),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                TextfieldGenerico(
                    value = uiState.email ?: "",
                    onValueChange = { onEvent(LoginEvent.EmailChange(it)) },
                    labelResource = R.string.placeholder_email,
                    icono = Icons.Default.Email,
                    errorMessagePass = uiState.emailErrorMessage
                )
                Box(Modifier.fillMaxWidth()){
                    MensajeDeErrorGenerico(uiState.emailErrorMessage)
                }

                // Password
                TextfieldPassword(
                    value = uiState.contrasena ?: "",
                    onValueChange = { onEvent(LoginEvent.ContrasenaChange(it)) },
                    labelResource = R.string.placeholder_contraseña,
                    icono = Icons.Default.Lock,
                    showPass = showPass,
                    onTogglePasswordVisibility = { showPass = !showPass },
                    errorMessagePass = uiState.contrasenaErrorMessage
                )
                Box(Modifier.fillMaxWidth()) {
                    MensajeDeErrorGenerico(uiState.contrasenaErrorMessage)
                }

                // Botón Continuar
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(LoginEvent.Login)
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
                    text = stringResource(R.string.registrarse),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            focusManager.clearFocus()
                            goToRegister()
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
fun PreviewLogin() {
    LoginScreenView(
        uiState = LoginUiState(),
        onEvent = {},
        goToRegister = {},
    )
}
