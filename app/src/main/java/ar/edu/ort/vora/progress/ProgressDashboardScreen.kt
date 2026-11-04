package ar.edu.ort.vora.progress

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.SegmentedControl
import ar.edu.ort.vora.ui.components.StatusTag
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.home.TabScreen
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraSurfaceMuted
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary


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

// Selector de período compartido por las pantallas de progreso
@Composable
internal fun PeriodSelector(period: String, onSelect: (String) -> Unit) {
    SegmentedControl(
        options = ProgressPeriods.map { stringResource(it.second) },
        selectedIndex = ProgressPeriods.indexOfFirst { it.first == period },
        onSelect = { onSelect(ProgressPeriods[it].first) },
    )
}

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

    TabScreen {
        // Encabezado
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.titleGap)) {
            Text(
                text = stringResource(R.string.progress_dashboard_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = VoraTextPrimary
            )
            Text(
                text = stringResource(R.string.progress_dashboard_subtitle),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = VoraTextSecondary
            )
        }

        PeriodSelector(period) { period = it }

        // Resumen del período
        InfoCard {
            Text(
                text = stringResource(
                    when (period) {
                        "Month" -> R.string.progress_overview_month
                        "3 Months" -> R.string.progress_overview_3_months
                        else -> R.string.progress_overview_week
                    }
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = VoraTextMuted
            )
            Spacer(Modifier.height(Spacing.s12))
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                    OverviewMetric(
                        title = stringResource(R.string.progress_daily_avg_protein),
                        value = protein,
                        change = "↑ 9%",
                        modifier = Modifier.weight(1f)
                    )
                    OverviewMetric(
                        title = stringResource(R.string.progress_active_streak),
                        value = pluralStringResource(R.plurals.progress_streak_days, streak, streak),
                        change = "🔥",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // VORA Insight
        DarkPanel(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VoraOrb(size = 32.dp, glowAlpha = 0.3f)
                Text(
                    text = stringResource(R.string.progress_vora_insight),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = VoraMint,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Spacing.s10)
                )
                StatusTag(stringResource(R.string.progress_consistent_badge), dark = true, color = VoraLime)
            }

            Text(
                text = pluralStringResource(R.plurals.progress_dashboard_insight, workouts, workouts),
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier.clickable { onNavigate("progress/weekly") },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.s6)
            ) {
                Text(
                    text = stringResource(R.string.progress_see_weekly_review),
                    style = MaterialTheme.typography.labelLarge,
                    color = VoraLime
                )
                Icon(
                    painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = VoraLime,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Accesos a cada detalle
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s10)) {
            ProgressNavigationCard(
                icon = R.drawable.ic_nav_workout,
                title = stringResource(R.string.common_nav_workout),
                subtitle = stringResource(R.string.progress_workout_card_subtitle),
                onClick = { onNavigate("progress/workout") }
            )
            ProgressNavigationCard(
                icon = R.drawable.ic_nav_nutrition,
                title = stringResource(R.string.common_nav_nutrition),
                subtitle = stringResource(R.string.progress_nutrition_card_subtitle, protein),
                onClick = { onNavigate("progress/nutrition") }
            )
            ProgressNavigationCard(
                icon = R.drawable.ic_nav_progress,
                title = stringResource(R.string.progress_activity),
                subtitle = stringResource(R.string.progress_activity_card_subtitle, steps),
                onClick = { onNavigate("progress/health") }
            )
        }

        // Objetivo activo
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = VoraMintLight,
            border = BorderStroke(1.dp, VoraMint)
        ) {
            Column(
                modifier = Modifier.padding(Spacing.s16),
                verticalArrangement = Arrangement.spacedBy(Spacing.s8)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.ic_circle_dot),
                        contentDescription = null,
                        tint = VoraGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(R.string.progress_active_goal),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = VoraTextPrimary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = Spacing.s8)
                    )
                    Text(
                        text = stringResource(R.string.progress_goal_complete, 68),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = VoraTextPrimary
                    )
                }
                Text(
                    text = stringResource(R.string.common_goal_strength_fitness),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = VoraTextPrimary
                )
                Text(
                    text = stringResource(R.string.progress_goal_description),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = VoraTextSecondary
                )
            }
        }
    }
}

// Métrica del resumen: título arriba y valor con la variación en la misma línea.
@Composable
private fun OverviewMetric(
    title: String,
    value: String,
    change: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(VoraSurfaceMuted, MaterialTheme.shapes.small)
            .padding(horizontal = Spacing.s12, vertical = Spacing.s10),
        verticalArrangement = Arrangement.spacedBy(Spacing.s4)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = VoraTextSecondary,
            maxLines = 1
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = VoraTextPrimary,
                maxLines = 1,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = change,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = VoraMint,
                maxLines = 1,
                modifier = Modifier
                    .alignByBaseline()
                    .padding(start = Spacing.s6)
            )
        }
    }
}

@Composable
private fun ProgressNavigationCard(
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    InfoCard(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(VoraBackground, PillShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(icon),
                    contentDescription = null,
                    tint = VoraTextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.s12),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = VoraTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = VoraTextSecondary
                )
            }
            Text(
                text = stringResource(R.string.progress_view),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = VoraTextPrimary
            )
            Icon(
                painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = VoraTextPrimary,
                modifier = Modifier
                    .padding(start = Spacing.s4)
                    .size(14.dp)
                    .rotate(180f)
            )
        }
    }
}
