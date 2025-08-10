package com.cashcontrol.presentation.transaccion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowDownward
import androidx.compose.material.icons.twotone.ArrowUpward
import androidx.compose.material.icons.twotone.CheckBox
import androidx.compose.material.icons.twotone.CheckBoxOutlineBlank
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TransaccionCard(
    transaccionId: Long,
    monto: Double,
    desripcionCategoria: String,
    tipoCategoria: String,
    descripcionTransaccion: String,
    fecha: String,
    estadoInicialUsoEnSugerencia: Boolean,
    onSuggestionChange: (Boolean) -> Unit = {},
    onEdit: (Long) -> Unit,
    onDelete: () -> Unit,
) {
    val (useInSuggestion, setUseInSuggestion) = remember {
        mutableStateOf(estadoInicialUsoEnSugerencia)
    }

    fun toggleSugerencia() {
        val newValue = !useInSuggestion
        setUseInSuggestion(newValue)
        onSuggestionChange(newValue)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,

        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Fecha y checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (tipoCategoria == "GASTOS") Icons.TwoTone.ArrowDownward
                        else Icons.TwoTone.ArrowUpward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(30.dp)
                    )
                    // Monto
                    Text(
                        text = String.format(Locale.US, "%.2f", monto),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = fecha,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Usar en sugerencia",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        IconButton(
                            onClick = { toggleSugerencia() },
                            modifier = Modifier.size(26.dp),
                        ) {
                            Icon(
                                imageVector = if (useInSuggestion) Icons.TwoTone.CheckBox
                                else Icons.TwoTone.CheckBoxOutlineBlank,
                                contentDescription = "Usar en sugerencia",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                // Categoría
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Categoría: ")
                        }
                        append(desripcionCategoria)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Descripción
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Descripción: ")
                            }
                            append(descripcionTransaccion)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(Modifier.width(8.dp))

                    IconButton(
                        onClick = { onEdit(transaccionId) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    IconButton(
                        onClick = { onDelete() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun TransaccionCardPreview() {
    MaterialTheme {
        TransaccionCard(
            monto = 30000.51,
            desripcionCategoria = "Ingresos fijos",
            descripcionTransaccion = "Pago mensual del trabajo",
            fecha = "04 / 10 / 2025",
            estadoInicialUsoEnSugerencia = false,
            onSuggestionChange = { },
            tipoCategoria = "INGRESOS",
            onEdit = { },
            onDelete = { },
            transaccionId = 1,
        )
    }
}