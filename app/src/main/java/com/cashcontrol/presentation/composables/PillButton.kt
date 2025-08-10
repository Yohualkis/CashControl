package com.cashcontrol.presentation.composables

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PillButton(
    texto: String,
    estadoInicialSeleccion: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (estadoInicialSeleccion) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            backgroundColor = if (estadoInicialSeleccion) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(texto)
    }
}

@Composable
@Preview
fun PreviewPillButton(){
    PillButton(
        texto = "Ejemplo",
        onClick = {}
    )
}