
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R

private val DashboardGreen = Color(0xFF103F35)
private val DashboardMint = Color(0xFF65D6B0)
private val DashboardBackground = Color(0xFFF7F8F6)
private val DashboardGray = Color(0xFF7A8580)
private val DashboardBorder = Color(0xFFE4EAE6)
private val DashboardLight = Color(0xFFF2F5F2)
private val DashboardLime = Color(0xFFBDEB70)

// Clave interna del período y su texto visible (compartido por las pantallas de progreso)
internal val ProgressPeriods = listOf(
    "Week" to R.string.progress_period_week,
    "Month" to R.string.progress_period_month,
    "3 Months" to R.string.progress_period_3_months
)

// Iniciales de lunes a domingo para los gráficos
internal val WeekDayInitials = listOf(
    R.string.progress_day_initial_mon,
    R.string.progress_day_initial_tue,
    R.string.progress_day_initial_wed,
    R.string.progress_day_initial_thu,
    R.string.progress_day_initial_fri,
    R.string.progress_day_initial_sat,
    R.string.progress_day_initial_sun
)

@Composable
fun ProgressDashboardScreen(
    onNavigate: (String) -> Unit = {}
) {
    var period by remember { mutableStateOf("Week") }

    val workouts = when (period) {
        "Month" -> 16
        "3 Months" -> 42
        else -> 4
    }

    val steps = when (period) {
        "Month" -> stringResource(R.string.common_number, 8120)
        "3 Months" -> stringResource(R.string.common_number, 7650)
        else -> stringResource(R.string.common_number, 7842)
    }

    val protein = when (period) {
        "Month" -> stringResource(R.string.common_value_g, 101)
        "3 Months" -> stringResource(R.string.common_value_g, 98)
        else -> stringResource(R.string.common_value_g, 104)
    }

    val streak = when (period) {
        "Month" -> 12
        "3 Months" -> 18
        else -> 4
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Encabezado
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = stringResource(R.string.progress_dashboard_title),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF18231F)
            )

            Text(
                text = stringResource(R.string.progress_dashboard_subtitle),
                fontSize = 13.sp,
                color = DashboardGray
            )
        }

        // Selector de período
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            ProgressPeriods
                .forEachIndexed { index, (option, label) ->
                    SegmentedButton(
                        selected = period == option,
                        onClick = { period = option },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = 3
                        ),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = DashboardGreen,
                            activeContentColor = Color.White,
                            inactiveContainerColor = Color.White,
                            inactiveContentColor = DashboardGray,
                            activeBorderColor = DashboardGreen,
                            inactiveBorderColor = DashboardBorder
                        )
                    ) {
                        Text(
                            text = stringResource(label),
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
                DashboardBorder
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(
                        when (period) {
                            "Month" -> R.string.progress_overview_month
                            "3 Months" -> R.string.progress_overview_3_months
                            else -> R.string.progress_overview_week
                        }
                    ),
                    fontSize = 11.sp,
                    color = DashboardGray,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OverviewMetric(
                        title = stringResource(R.string.progress_workouts_completed),
                        value = pluralStringResource(R.plurals.progress_workouts_count, workouts, workouts),
                        change = "↑ 1",
                        modifier = Modifier.weight(1f)
                    )

                    OverviewMetric(
                        title = stringResource(R.string.progress_avg_daily_steps),
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
                        title = stringResource(R.string.progress_daily_avg_protein),
                        value = protein,
                        change = "↑ 9%",
                        modifier = Modifier.weight(1f)
                    )

                    OverviewMetric(
                        title = stringResource(R.string.progress_active_streak),
                        value = pluralStringResource(R.plurals.progress_streak_days, streak, streak),
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
                containerColor = DashboardGreen
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
                                color = Color(0xFF1B6655),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = DashboardMint,
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
                        text = stringResource(R.string.progress_vora_insight),
                        color = DashboardMint,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = stringResource(R.string.progress_consistent_badge),
                        color = DashboardLime,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                color = Color(0xFF285449),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(
                                horizontal = 9.dp,
                                vertical = 5.dp
                            )
                    )
                }

                Text(
                    text = pluralStringResource(R.plurals.progress_dashboard_insight, workouts, workouts),
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
                        text = stringResource(R.string.progress_see_weekly_review),
                        color = DashboardLime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "→",
                        color = DashboardLime,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Acceso a Workout
        ProgressNavigationCard(
            icon = "♧",
            title = stringResource(R.string.common_nav_workout),
            subtitle = stringResource(R.string.progress_workout_card_subtitle),
            onClick = {
                onNavigate("progress/workout")
            }
        )

        // Acceso a Nutrition
        ProgressNavigationCard(
            icon = "◉",
            title = stringResource(R.string.common_nav_nutrition),
            subtitle = stringResource(R.string.progress_nutrition_card_subtitle, protein),
            onClick = {
                onNavigate("progress/nutrition")
            }
        )

        // Acceso a Activity
        ProgressNavigationCard(
            icon = "↗",
            title = stringResource(R.string.progress_activity),
            subtitle = stringResource(R.string.progress_activity_card_subtitle, steps),
            onClick = {
                onNavigate("progress/health")
            }
        )

        // Objetivo activo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F4EC)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                DashboardMint
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
                        color = DashboardGreen
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = stringResource(R.string.progress_active_goal),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashboardGreen,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = stringResource(R.string.progress_goal_complete, 68),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DashboardGreen
                    )
                }

                LinearProgressIndicator(
                    progress = { 0.68f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp),
                    color = DashboardGreen,
                    trackColor = Color(0xFFC8DDD2)
                )

                Text(
                    text = stringResource(R.string.common_goal_strength_fitness),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF18231F)
                )

                Text(
                    text = stringResource(R.string.progress_goal_description),
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = DashboardGray
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
                color = DashboardLight,
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
            color = DashboardGray,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF18231F),
            maxLines = 1
        )

        if (change.isNotEmpty()) {
            Text(
                text = change,
                fontSize = 10.sp,
                color = Color(0xFF319B75),
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
            DashboardBorder
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
                        color = DashboardLight,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DashboardGreen
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
                    color = Color(0xFF18231F)
                )

                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = DashboardGray
                )
            }

            Text(
                text = stringResource(R.string.progress_view_arrow),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = DashboardGreen
            )
        }
    }
}
