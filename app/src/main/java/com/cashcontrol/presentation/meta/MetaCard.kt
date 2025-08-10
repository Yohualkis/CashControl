package com.cashcontrol.presentation.meta

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MetaCard(
    nombreMeta: String,
    imagenMeta: Int, // Reemplaza con tu recurso de imagen
    montoTotal: Double,
    abonado: Double,
    modifier: Modifier = Modifier,
) {
    val progreso = abonado / montoTotal

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            // Imagen de la meta
            Image(
                painter = painterResource(id = imagenMeta),
                contentDescription = "Imagen de la meta: $nombreMeta",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                // Nombre de la meta
            )

            // Montos
            Text(text = "Carro: $${montoTotal.toInt()}")
            Text(text = "Abono: $${abonado.toInt()}")

            // Barra de progreso
            LinearProgressIndicator(
                progress = { progreso.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outline,
                trackColor = MaterialTheme.colorScheme.tertiary,
                strokeCap = StrokeCap.Round,
            )

            // Porcentaje (opcional)
            Text(
                text = "${(progreso * 100).toInt()}% completado",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun MetaCardPreview() {
    MaterialTheme {
        MetaCard(
            nombreMeta = "Camry 2018",
            imagenMeta = 1, // Reemplaza con tu recurso
            montoTotal = 100000.0,
            abonado = 75000.0
        )
    }
}