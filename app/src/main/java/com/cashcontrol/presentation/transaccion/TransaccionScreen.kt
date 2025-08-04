package com.cashcontrol.presentation.transaccion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.Article
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.twotone.Category
import androidx.compose.material.icons.twotone.MonetizationOn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cashcontrol.R
import com.cashcontrol.presentation.composables.CashControlAppBar
import com.cashcontrol.presentation.composables.MensajeDeErrorGenerico
import com.cashcontrol.presentation.composables.TextfieldGenerico
import com.cashcontrol.presentation.composables.TextfieldNumericoGenerico

@Composable
fun TransaccionScreen(
    viewModel: TransaccionViewModel = hiltViewModel(),
    transaccionId: Long?,
    goBack: () -> Unit,
) {
    LaunchedEffect(transaccionId) {
        transaccionId?.let {
            viewModel.selectedTransaccion(transaccionId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TransaccionFormulario(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaccionFormulario(
    uiState: TransaccionUiState,
    onEvent: (TransaccionEvent) -> Unit,
    goBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceDim,
        topBar = {
            CashControlAppBar(
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                goBack = { goBack() },
                titulo = stringResource(R.string.formulario_transaccion),
            )
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusManager.clearFocus() }
        ) {
            var expanded by remember { mutableStateOf(false) }

            // CAMPO MONTO
            TextfieldNumericoGenerico(
                value = uiState.monto?.takeIf { it > 0.0 }?.toString() ?: "",
                onValueChange = {
                    onEvent(TransaccionEvent.MontoChange(it.toDouble()))
                },
                labelResource = R.string.campo_monto,
                icono = Icons.TwoTone.MonetizationOn,
                errorMessagePass = uiState.errorMonto,
            )
            MensajeDeErrorGenerico(uiState.errorMonto)

            // CAMPO DESCRIPCION
            TextfieldGenerico(
                value = uiState.descripcionTransaccion ?: "",
                onValueChange = {
                    onEvent(TransaccionEvent.DescripcionTransaccionChange(it))
                },
                labelResource = R.string.campo_descripcion,
                icono = Icons.AutoMirrored.TwoTone.Article,
                errorMessagePass = uiState.errorDescripcionTransaccion
            )
            MensajeDeErrorGenerico(uiState.errorDescripcionTransaccion)

            // CAMPO CATEGORIA
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = uiState.descripcionCategoria ?: "",
                    onValueChange = {
                        onEvent(
                            TransaccionEvent.CategoriaChange(
                                it.toLong(),
                                uiState.descripcionCategoria ?: ""
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.TwoTone.Category,
                            contentDescription = ""
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    label = {
                        Text(
                            text = "Categoría",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    trailingIcon = {
                        TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.run {
                        textFieldColors(
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            errorCursorColor = MaterialTheme.colorScheme.error,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorLeadingIconColor = MaterialTheme.colorScheme.error,
                            errorTrailingIconColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    isError = !uiState.errorCategoria.isNullOrBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    uiState.listaAllCategorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.descripcion + " (${cat.tipo})") },
                            onClick = {
                                onEvent(
                                    TransaccionEvent.CategoriaChange(
                                        cat.categoriaId,
                                        cat.descripcion
                                    )
                                )
                                expanded = false
                            }
                        )
                    }
                }
            }
            MensajeDeErrorGenerico(uiState.errorCategoria)

            Spacer(modifier = Modifier.height(8.dp))

            // BTN GUARDAR
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onEvent(TransaccionEvent.Save)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(R.string.btn_guardar),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.btn_guardar),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            MensajeDeErrorGenerico(uiState.errorGeneral)
        }
    }
}

@Preview
@Composable
fun PreviewTransaccionFormulario() {
    TransaccionFormulario(
        uiState = TransaccionUiState(),
        onEvent = {},
        goBack = {}
    )
}