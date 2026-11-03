
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraMintSoft
import ar.edu.ort.vora.ui.theme.VoraSuccess
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary


@Composable
fun HealthTrendsScreen(
    onBack: () -> Unit = {}
) {
    val steps = listOf(55, 75, 78, 72, 92, 76, 76)
    val days = WeekDayInitials.map { stringResource(it) }

    Scaffold(
        containerColor = VoraBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(VoraBackground)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Encabezado
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
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

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.progress_health_title),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VoraTextPrimary
                        )

                        Text(
                            text = stringResource(R.string.progress_health_subtitle),
                            fontSize = 10.sp,
                            color = VoraTextSecondary
                        )
                    }

                    Text(
                        text = "•••",
                        fontSize = 18.sp,
                        color = VoraGreen
                    )
                }
            }

            // Métricas superiores
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
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
                            .padding(vertical = 12.dp, horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HealthSummaryMetric(
                            title = stringResource(R.string.progress_avg_steps),
                            value = stringResource(R.string.common_number, 7842),
                            modifier = Modifier.weight(1f)
                        )

                        HealthMetricDivider()

                        HealthSummaryMetric(
                            title = stringResource(R.string.progress_avg_distance),
                            value = stringResource(R.string.common_value_km, 5.6),
                            modifier = Modifier.weight(1f)
                        )

                        HealthMetricDivider()

                        HealthSummaryMetric(
                            title = stringResource(R.string.progress_active_kcal),
                            value = stringResource(R.string.common_value_kcal, 312),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Gráfico semanal
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.progress_health_weekly_steps),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VoraTextPrimary,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = stringResource(R.string.progress_health_goal, stringResource(R.string.common_number, 8000)),
                                fontSize = 10.sp,
                                color = VoraMint,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            steps.forEachIndexed { index, value ->
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(13.dp)
                                            .height((value * 0.55f).dp)
                                            .background(
                                                color = if (value >= 78) {
                                                    VoraGreen
                                                } else {
                                                    VoraMint
                                                },
                                                shape = RoundedCornerShape(
                                                    topStart = 3.dp,
                                                    topEnd = 3.dp
                                                )
                                            )
                                    )

                                    Spacer(Modifier.height(5.dp))

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
            }

            // Consejo de VORA
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = VoraMintLight,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = VoraMint,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(11.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = VoraMintSoft,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = VoraMint,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "V",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = VoraGreen
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = stringResource(R.string.progress_health_insight),
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        color = VoraGreen
                    )
                }
            }

            // Señales de hábitos
            item {
                Text(
                    text = stringResource(R.string.progress_habit_signals),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VoraTextSecondary
                )
            }

            item {
                HabitSignalRow(
                    icon = "▤",
                    title = stringResource(R.string.progress_habit_sleep),
                    status = stringResource(R.string.progress_status_improving),
                    improving = true
                )
            }

            item {
                HabitSignalRow(
                    icon = "➤",
                    title = stringResource(R.string.progress_habit_movement),
                    status = stringResource(R.string.progress_status_consistent),
                    improving = false
                )
            }

            item {
                HabitSignalRow(
                    icon = "♧",
                    title = stringResource(R.string.progress_habit_workouts),
                    status = stringResource(R.string.progress_status_improving),
                    improving = true
                )
            }

            item {
                HabitSignalRow(
                    icon = "◉",
                    title = stringResource(R.string.progress_habit_protein),
                    status = stringResource(R.string.progress_status_improving),
                    improving = true
                )
            }
        }
    }
}

@Composable
private fun HealthSummaryMetric(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 7.sp,
            color = VoraTextSecondary,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = VoraTextPrimary,
            maxLines = 1
        )
    }
}

@Composable
private fun HealthMetricDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(30.dp)
            .background(VoraBorder)
    )
}

@Composable
private fun HabitSignalRow(
    icon: String,
    title: String,
    status: String,
    improving: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(11.dp),
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
                .padding(horizontal = 10.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 15.sp,
                color = VoraGreen
            )

            Spacer(Modifier.width(9.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = VoraTextPrimary,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = status,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if (improving) {
                    VoraSuccess
                } else {
                    VoraTextSecondary
                }
            )
        }
    }
}
