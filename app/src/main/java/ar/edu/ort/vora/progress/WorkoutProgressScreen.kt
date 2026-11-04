
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraMintSoft
import ar.edu.ort.vora.ui.theme.VoraSurfaceMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary


@Composable
fun WorkoutProgressScreen(
    onBack: () -> Unit = {}
) {
    var period by remember { mutableStateOf("Week") }

    val sessions = when (period) {
        "Month" -> "16"
        "3 Months" -> "42"
        else -> "4"
    }

    val volume = when (period) {
        "Month" -> stringResource(R.string.common_value_kg, 32640)
        "3 Months" -> stringResource(R.string.common_value_kg, 91200)
        else -> stringResource(R.string.common_value_kg, 8420)
    }

    val averageTime = when (period) {
        "Month" -> stringResource(R.string.common_value_min, 39)
        "3 Months" -> stringResource(R.string.common_value_min, 40)
        else -> stringResource(R.string.common_value_min, 41)
    }

    val chartValues = when (period) {
        "Month" -> listOf(48, 70, 58, 85, 76)
        "3 Months" -> listOf(62, 78, 55, 92, 82)
        else -> listOf(45, 58, 0, 68, 73)
    }

    val exercises = listOf(
        Triple(
            stringResource(R.string.progress_exercise_bench_press),
            stringResource(R.string.progress_weight_reps, 40, 10),
            stringResource(R.string.common_delta_kg, 2.5)
        ),
        Triple(
            stringResource(R.string.progress_exercise_cable_row),
            stringResource(R.string.progress_weight_reps, 45, 12),
            stringResource(R.string.common_delta_kg, 5.0)
        ),
        Triple(
            stringResource(R.string.progress_exercise_shoulder_press),
            stringResource(R.string.progress_weight_reps, 20, 10),
            stringResource(R.string.progress_stable)
        ),
        Triple(
            stringResource(R.string.progress_exercise_lateral_raise),
            stringResource(R.string.progress_weight_reps, 8, 12),
            stringResource(R.string.common_delta_kg, 1.0)
        )
    )

    Scaffold(
        containerColor = VoraBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(VoraBackground)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Encabezado
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(
                            horizontal = 4.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            text = "‹",
                            fontSize = 25.sp,
                            color = VoraGreen
                        )
                    }

                    Text(
                        text = stringResource(R.string.progress_workout_title),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = VoraGreen
                    )
                }

                // Selector de período
                Box(Modifier.padding(bottom = 12.dp)) { PeriodSelector(period) { period = it } }

                // Métricas principales
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WorkoutMetricCard(
                        label = stringResource(R.string.progress_sessions_caps),
                        value = sessions,
                        modifier = Modifier.weight(1f)
                    )

                    WorkoutMetricCard(
                        label = stringResource(R.string.progress_volume_caps),
                        value = volume,
                        modifier = Modifier.weight(1.25f)
                    )

                    WorkoutMetricCard(
                        label = stringResource(R.string.progress_avg_time),
                        value = averageTime,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Gráfico de volumen
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.progress_training_volume),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VoraTextPrimary
                        )

                        Text(
                            text = stringResource(R.string.progress_kg_per_session),
                            fontSize = 9.sp,
                            color = VoraTextSecondary
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.progress_volume_change),
                            color = VoraGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(92.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            val days = WeekDayInitials.take(5).map { stringResource(it) }

                            chartValues.forEachIndexed { index, value ->
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.48f)
                                            .height(
                                                if (value == 0) {
                                                    3.dp
                                                } else {
                                                    (value * 0.65f).dp
                                                }
                                            )
                                            .background(
                                                color = if (value == 0) {
                                                    VoraSurfaceMuted
                                                } else {
                                                    VoraGreen
                                                },
                                                shape = RoundedCornerShape(
                                                    topStart = 4.dp,
                                                    topEnd = 4.dp
                                                )
                                            )
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = days[index],
                                        fontSize = 9.sp,
                                        color = VoraTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }

                // Mejor marca personal
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VoraMintLight
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraMintSoft
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .background(
                                    Color.White,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🏆",
                                fontSize = 17.sp
                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.progress_personal_best),
                                color = VoraGreen,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = stringResource(R.string.progress_exercise_seated_cable_row),
                                color = VoraTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = stringResource(R.string.progress_personal_best_detail),
                                color = VoraTextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Consejo de VORA
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VoraMintLight
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    VoraMintSoft,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "V",
                                color = VoraGreen,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.progress_vora_advice),
                                color = VoraGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = stringResource(R.string.progress_workout_advice),
                                color = VoraGreen,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                // Lista de ejercicios
                Text(
                    text = stringResource(R.string.progress_exercise_progress),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoraTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    exercises.forEach { exercise ->
                        ExerciseProgressRow(
                            name = exercise.first,
                            details = exercise.second,
                            change = exercise.third
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun WorkoutMetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(66.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            VoraBorder
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 8.sp,
                color = VoraTextSecondary,
                maxLines = 1
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = VoraTextPrimary,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ExerciseProgressRow(
    name: String,
    details: String,
    change: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            VoraBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VoraTextPrimary
                )

                Text(
                    text = details,
                    fontSize = 9.sp,
                    color = VoraTextSecondary
                )
            }

            Text(
                text = change,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                color = VoraGreen,
                modifier = Modifier
                    .background(
                        color = VoraMintLight,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(
                        horizontal = 7.dp,
                        vertical = 5.dp
                    )
            )
        }
    }
}
