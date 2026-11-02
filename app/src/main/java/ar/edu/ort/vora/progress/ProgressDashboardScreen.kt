
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraGreenAccent
import ar.edu.ort.vora.ui.theme.VoraGreenRaised
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraMintSoft
import ar.edu.ort.vora.ui.theme.VoraSuccess
import ar.edu.ort.vora.ui.theme.VoraSurfaceMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun ProgressDashboardScreen(
    onNavigate: (String) -> Unit = {}
) {
    var period by remember { mutableStateOf("Week") }

    val workouts = when (period) {
        "Month" -> "16"
        "3 Months" -> "42"
        else -> "4"
    }

    val steps = when (period) {
        "Month" -> "8,120"
        "3 Months" -> "7,650"
        else -> "7,842"
    }

    val protein = when (period) {
        "Month" -> "101g"
        "3 Months" -> "98g"
        else -> "104g"
    }

    val streak = when (period) {
        "Month" -> "12 days 🔥"
        "3 Months" -> "18 days 🔥"
        else -> "4 days 🔥"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoraBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Encabezado
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "Your progress",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = VoraTextPrimary
            )

            Text(
                text = "Small improvements add up.",
                fontSize = 13.sp,
                color = VoraTextSecondary
            )
        }

        // Selector de período
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Week", "Month", "3 Months")
                .forEachIndexed { index, option ->
                    SegmentedButton(
                        selected = period == option,
                        onClick = { period = option },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = VoraGreen,
                            activeContentColor = Color.White,
                            inactiveContainerColor = Color.White,
                            inactiveContentColor = VoraTextSecondary,
                            activeBorderColor = VoraGreen,
                            inactiveBorderColor = VoraBorder
                        )
                    ) {
                        Text(
                            text = option,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                }
        }

        // Resumen semanal
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
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
                Text(
                    text = if (period == "Week") {
                        "THIS WEEK OVERVIEW"
                    } else {
                        "${period.uppercase()} OVERVIEW"
                    },
                    fontSize = 11.sp,
                    color = VoraTextSecondary,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OverviewMetric(
                        title = "Workouts completed",
                        value = "$workouts workouts",
                        change = "↑ 1",
                        modifier = Modifier.weight(1f)
                    )

                    OverviewMetric(
                        title = "Avg Daily Steps",
                        value = steps,
                        change = "↑ 6%",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OverviewMetric(
                        title = "Daily Avg Protein",
                        value = protein,
                        change = "↑ 9%",
                        modifier = Modifier.weight(1f)
                    )

                    OverviewMetric(
                        title = "Active Streak",
                        value = streak,
                        change = "",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // VORA Insight
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = VoraGreen
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = VoraGreenAccent,
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
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "VORA Insight",
                        color = VoraMint,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "CONSISTENT",
                        color = VoraLime,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                color = VoraGreenRaised,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(
                                horizontal = 9.dp,
                                vertical = 5.dp
                            )
                    )
                }

                Text(
                    text = "\"You've completed $workouts workouts this " +
                            "period. Keep building your routine and your " +
                            "consistency is trending in the right direction.\"",
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigate("progress/weekly")
                        }
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "See weekly review",
                        color = VoraLime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "→",
                        color = VoraLime,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Acceso a Workout
        ProgressNavigationCard(
            icon = "♧",
            title = "Workout",
            subtitle = "3 sessions • 2,340 kg volume",
            onClick = {
                onNavigate("progress/workout")
            }
        )

        // Acceso a Nutrition
        ProgressNavigationCard(
            icon = "◉",
            title = "Nutrition",
            subtitle = "$protein protein • 74% avg target",
            onClick = {
                onNavigate("progress/nutrition")
            }
        )

        // Acceso a Activity
        ProgressNavigationCard(
            icon = "↗",
            title = "Activity",
            subtitle = "$steps steps • 5.6 km daily avg",
            onClick = {
                onNavigate("progress/health")
            }
        )

        // Objetivo activo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = VoraMintLight
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                VoraMint
            )
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "◎",
                        fontSize = 15.sp,
                        color = VoraGreen
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "ACTIVE GOAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = VoraGreen,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "68% complete",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VoraGreen
                    )
                }

                LinearProgressIndicator(
                    progress = { 0.68f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp),
                    color = VoraGreen,
                    trackColor = VoraMintSoft
                )

                Text(
                    text = "Build strength & improve fitness",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = VoraTextPrimary
                )

                Text(
                    text = "You're building consistency across workouts " +
                            "and nutrition.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = VoraTextSecondary
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun OverviewMetric(
    title: String,
    value: String,
    change: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = VoraSurfaceMuted,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            ),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            color = VoraTextSecondary,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = VoraTextPrimary,
            maxLines = 1
        )

        if (change.isNotEmpty()) {
            Text(
                text = change,
                fontSize = 10.sp,
                color = VoraSuccess,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ProgressNavigationCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = VoraSurfaceMuted,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoraGreen
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VoraTextPrimary
                )

                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = VoraTextSecondary
                )
            }

            Text(
                text = "View  ›",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = VoraGreen
            )
        }
    }
}
