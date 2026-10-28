
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HealthGreen = Color(0xFF123F35)
private val HealthBackground = Color(0xFFF7F8F5)
private val HealthMuted = Color(0xFF7A8580)
private val HealthBorder = Color(0xFFE4EAE6)
private val HealthMint = Color(0xFF65D6B0)
private val HealthLightMint = Color(0xFFE3F5EC)

@Composable
fun HealthTrendsScreen(
    onBack: () -> Unit = {}
) {
    val steps = listOf(55, 75, 78, 72, 92, 76, 76)
    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    Scaffold(
        containerColor = HealthBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(HealthBackground)
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
                            color = HealthGreen
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Health trends",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF18231F)
                        )

                        Text(
                            text = "See how your daily habits are changing.",
                            fontSize = 10.sp,
                            color = HealthMuted
                        )
                    }

                    Text(
                        text = "•••",
                        fontSize = 18.sp,
                        color = HealthGreen
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
                        HealthBorder
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HealthSummaryMetric(
                            title = "AVG STEPS",
                            value = "7,842",
                            modifier = Modifier.weight(1f)
                        )

                        HealthMetricDivider()

                        HealthSummaryMetric(
                            title = "AVG DISTANCE",
                            value = "5.6 km",
                            modifier = Modifier.weight(1f)
                        )

                        HealthMetricDivider()

                        HealthSummaryMetric(
                            title = "ACTIVE KCAL",
                            value = "312 kcal",
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
                        HealthBorder
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
                                text = "Weekly Steps Trend",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF18231F),
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "Goal: 8,000",
                                fontSize = 10.sp,
                                color = HealthMint,
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
                                                    HealthGreen
                                                } else {
                                                    HealthMint
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
                                        color = HealthMuted
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
                            color = HealthLightMint,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = HealthMint,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(11.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = Color(0xFFB9E8D0),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = HealthMint,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "V",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = HealthGreen
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "\"Your activity has been more consistent over " +
                                "the last two weeks. Your workout days also tend " +
                                "to be your most active days.\"",
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        color = HealthGreen
                    )
                }
            }

            // Señales de hábitos
            item {
                Text(
                    text = "HABIT SIGNALS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HealthMuted
                )
            }

            item {
                HabitSignalRow(
                    icon = "▤",
                    title = "Sleep Quality",
                    status = "Improving ↗",
                    improving = true
                )
            }

            item {
                HabitSignalRow(
                    icon = "➤",
                    title = "Daily Movement",
                    status = "Consistent  ↕",
                    improving = false
                )
            }

            item {
                HabitSignalRow(
                    icon = "♧",
                    title = "Workout Frequency",
                    status = "Improving ↗",
                    improving = true
                )
            }

            item {
                HabitSignalRow(
                    icon = "◉",
                    title = "Protein Intake",
                    status = "Improving ↗",
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
            color = HealthMuted,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF18231F),
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
            .background(HealthBorder)
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
            HealthBorder
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
                color = HealthGreen
            )

            Spacer(Modifier.width(9.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF18231F),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = status,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if (improving) {
                    Color(0xFF27A66B)
                } else {
                    HealthMuted
                }
            )
        }
    }
}
