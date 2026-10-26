
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DashboardGreen = Color(0xFF103F35)
private val DashboardMint = Color(0xFFBDEB70)
private val DashboardBackground = Color(0xFFF7F8F6)
private val DashboardGray = Color(0xFF68736F)

@Composable
fun ProgressDashboardScreen(
    onNavigate: (String) -> Unit = {}
) {
    // Período seleccionado en el selector.
    var period by remember { mutableStateOf("Semana") }

    // Datos de demostración según el período seleccionado.
    val workouts = when (period) {
        "Mes" -> "16"
        "Trimestre" -> "42"
        else -> "4"
    }

    val steps = when (period) {
        "Mes" -> "8.120"
        "Trimestre" -> "7.650"
        else -> "7.842"
    }

    val calories = when (period) {
        "Mes" -> "1.890"
        "Trimestre" -> "1.950"
        else -> "1.820"
    }

    val consistency = when (period) {
        "Mes" -> "82%"
        "Trimestre" -> "76%"
        else -> "80%"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Text(
            text = "PROGRESO",
            color = DashboardGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Tu evolución",
            color = DashboardGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Revisá tus hábitos y cómo venís avanzando.",
            color = DashboardGray,
            fontSize = 14.sp
        )

        // Selector de período.
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Semana", "Mes", "Trimestre")
                .forEachIndexed { index, option ->

                    SegmentedButton(
                        selected = period == option,
                        onClick = { period = option },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 3
                        )
                    ) {
                        Text(
                            text = option,
                            fontSize = 12.sp
                        )
                    }
                }
        }

        Text(
            text = "Indicadores generales",
            color = DashboardGreen,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Primera fila de indicadores.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardMetric(
                title = "Entrenamientos",
                value = workouts,
                detail = if (period == "Semana") {
                    "Esta semana"
                } else {
                    "Período"
                },
                modifier = Modifier.weight(1f)
            )

            DashboardMetric(
                title = "Pasos promedio",
                value = steps,
                detail = "Por día",
                modifier = Modifier.weight(1f)
            )
        }

        // Segunda fila de indicadores.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardMetric(
                title = "Calorías",
                value = calories,
                detail = "Promedio diario",
                modifier = Modifier.weight(1f)
            )

            DashboardMetric(
                title = "Constancia",
                value = consistency,
                detail = "Objetivos cumplidos",
                modifier = Modifier.weight(1f)
            )
        }

        // Tarjeta con la recomendación de VORA.
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = DashboardGreen
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "RECOMENDACIÓN VORA",
                    color = DashboardMint,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "La constancia es la clave",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Mantené una rutina realista, registrá tus " +
                            "entrenamientos y revisá tus objetivos al final " +
                            "de cada semana.",
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }

        Text(
            text = "Resumen del período",
            color = DashboardGreen,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Tarjeta de cumplimiento de objetivos.
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Cumplimiento de objetivos",
                    color = DashboardGreen,
                    fontWeight = FontWeight.SemiBold
                )

                LinearProgressIndicator(
                    progress = { 0.8f },
                    modifier = Modifier.fillMaxWidth(),
                    color = DashboardGreen,
                    trackColor = Color(0xFFE5EAE6)
                )

                Text(
                    text = "80% de cumplimiento estimado",
                    color = DashboardGray,
                    fontSize = 13.sp
                )
            }
        }

        // Accesos a las pantallas secundarias de progreso.
        Text(
            text = "Explorar progreso",
            color = DashboardGreen,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        NavigationCard(
            title = "Progreso del entrenamiento",
            description = "Consultá tu actividad física.",
            buttonText = "Ver entrenamiento",
            onClick = {
                onNavigate("progress/workout")
            }
        )

        NavigationCard(
            title = "Progreso de nutrición",
            description = "Revisá tus objetivos nutricionales.",
            buttonText = "Ver nutrición",
            onClick = {
                onNavigate("progress/nutrition")
            }
        )

        NavigationCard(
            title = "Tendencias de salud",
            description = "Explorá tus indicadores de salud.",
            buttonText = "Ver tendencias",
            onClick = {
                onNavigate("progress/health")
            }
        )

        NavigationCard(
            title = "Resumen semanal",
            description = "Consultá el resumen de tu semana.",
            buttonText = "Ver resumen",
            onClick = {
                onNavigate("progress/weekly")
            }
        )

        Text(
            text = "Datos de demostración: no representan mediciones reales.",
            color = DashboardGray,
            fontSize = 11.sp
        )
    }
}

// Componente reutilizable para mostrar una métrica.
@Composable
private fun DashboardMetric(
    title: String,
    value: String,
    detail: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = DashboardGray,
                fontSize = 12.sp
            )

            Text(
                text = value,
                color = DashboardGreen,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = detail,
                color = DashboardGray,
                fontSize = 11.sp
            )
        }
    }
}

// Componente reutilizable para acceder a otra pantalla.
@Composable
private fun NavigationCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = DashboardGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = description,
                color = DashboardGray,
                fontSize = 13.sp
            )

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DashboardGreen
                )
            ) {
                Text(buttonText)
            }
        }
    }
}
