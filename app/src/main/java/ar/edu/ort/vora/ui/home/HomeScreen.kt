package ar.edu.ort.vora.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.ButtonSize
import ar.edu.ort.vora.ui.components.CoachNote
import ar.edu.ort.vora.ui.components.DarkPanel
import ar.edu.ort.vora.ui.components.IconCircleButton
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.LimeButton
import ar.edu.ort.vora.ui.components.MacroProgress
import ar.edu.ort.vora.ui.components.PanelButton
import ar.edu.ort.vora.ui.components.PrimaryButton
import ar.edu.ort.vora.ui.components.ProgressRing
import ar.edu.ort.vora.ui.components.StatusDot
import ar.edu.ort.vora.ui.components.StatusTag
import ar.edu.ort.vora.ui.components.VoraOrb
import ar.edu.ort.vora.ui.theme.PanelTitleStyle
import ar.edu.ort.vora.ui.theme.PillShape
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintTranslucent
import ar.edu.ort.vora.ui.theme.VoraSurface
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

// Dashboard de demostración: datos de ejemplo de Figma y accesos a cada flujo.
@Composable
fun HomeScreen(
    onNotifications: () -> Unit,
    onVoiceSession: () -> Unit,
    onStartWorkout: () -> Unit,
    onLogMeal: () -> Unit,
    onTodayOverview: () -> Unit,
) {
    TabScreen {
        Header(onNotifications)
        CoachPanel(onVoiceSession, onStartWorkout, onLogMeal)
        CoachNote(stringResource(R.string.home_coach_note))
        Text(
            stringResource(R.string.common_todays_overview),
            modifier = Modifier.padding(top = Spacing.s4),
            style = MaterialTheme.typography.titleMedium,
            color = VoraTextPrimary,
        )
        DailyOverview(onTodayOverview)
        ActivityStats(onTodayOverview)
        RecommendedWorkout(onStartWorkout)
    }
}

// Pantalla de pestaña: scroll, ancho máximo centrado y márgenes de Figma. La barra inferior la pone VoraNavHost.
@Composable
internal fun TabScreen(content: @Composable ColumnScope.() -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(VoraBackground)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            Modifier
                .widthIn(max = Spacing.maxContentWidth)
                .fillMaxWidth()
                .padding(horizontal = Spacing.screen, vertical = Spacing.s16),
            verticalArrangement = Arrangement.spacedBy(Spacing.groupGap),
            content = content,
        )
    }
}

@Composable
private fun Header(onNotifications: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(40.dp)
                .background(VoraMintTranslucent, PillShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(stringResource(R.string.home_user_initials), style = MaterialTheme.typography.labelLarge, color = VoraGreen)
        }
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = Spacing.s12),
        ) {
            Text(stringResource(R.string.home_greeting), style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            Text(stringResource(R.string.common_demo_user_name), style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Box {
            IconCircleButton(R.drawable.ic_bell, stringResource(R.string.common_notifications), onNotifications)
            StatusDot(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 7.dp, end = 8.dp)
                    .size(6.dp),
                color = VoraMint,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CoachPanel(onVoiceSession: () -> Unit, onStartWorkout: () -> Unit, onLogMeal: () -> Unit) {
    DarkPanel(modifier = Modifier.padding(top = Spacing.s4)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            VoraOrb(size = 28.dp, glowAlpha = 0.3f)
            Text(
                stringResource(R.string.coach_title),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.s8),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = VoraMint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            StatusTag(stringResource(R.string.home_ai_online), dark = true)
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Text(stringResource(R.string.home_coach_question), style = PanelTitleStyle.copy(fontWeight = FontWeight.Medium))
            Text(
                stringResource(R.string.home_coach_hint),
                style = MaterialTheme.typography.bodySmall,
                color = VoraSurface.copy(alpha = 0.6f),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            LimeButton(stringResource(R.string.home_start_voice_session), onVoiceSession, Modifier.fillMaxWidth(), icon = R.drawable.ic_mic, alignStart = true)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s8), verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                PanelButton(stringResource(R.string.common_start_my_workout), onStartWorkout, size = ButtonSize.Small)
                PanelButton(stringResource(R.string.home_log_breakfast), onLogMeal, size = ButtonSize.Small)
            }
        }
    }
}

// Anillo del objetivo diario y resumen de nutrición, con el mismo alto.
@Composable
private fun DailyOverview(onClick: () -> Unit) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(Spacing.groupGap),
    ) {
        InfoCard(Modifier.weight(0.32f).fillMaxHeight()) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                ProgressRing(0.72f, size = 64.dp) {
                    Text(stringResource(R.string.home_percent, 72), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VoraTextPrimary)
                }
                Spacer(Modifier.height(Spacing.s8))
                Text(stringResource(R.string.home_daily_goal), style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            }
        }
        InfoCard(Modifier.weight(0.68f).fillMaxHeight()) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.common_nav_nutrition), Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = VoraTextPrimary)) { append(stringResource(R.string.common_number, 1420)) }
                            append(stringResource(R.string.home_kcal_goal, 2100))
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = VoraTextMuted,
                    )
                }
                Spacer(Modifier.height(Spacing.s10))
                MacroProgress(stringResource(R.string.common_protein), stringResource(R.string.common_value_g, 104), 104 / 140f, VoraGreen, goal = stringResource(R.string.common_value_g, 140))
                Spacer(Modifier.height(Spacing.s8))
                MacroProgress(stringResource(R.string.common_carbs), stringResource(R.string.common_value_g, 152), 152 / 220f, VoraMint, goal = stringResource(R.string.common_value_g, 220))
            }
        }
    }
}

@Composable
private fun ActivityStats(onClick: () -> Unit) {
    InfoCard(
        Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        padding = Spacing.s12,
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            Stat(stringResource(R.string.home_steps), stringResource(R.string.common_number, 7842))
            VerticalDivider(color = VoraBorder)
            Stat(stringResource(R.string.home_distance), stringResource(R.string.common_value_km, 5.6))
            VerticalDivider(color = VoraBorder)
            Stat(stringResource(R.string.home_active_kcal), stringResource(R.string.common_value_kcal, 312))
        }
    }
}

@Composable
private fun RowScope.Stat(label: String, value: String) {
    Column(
        Modifier
            .weight(1f)
            .padding(horizontal = Spacing.s4)
            .padding(start = Spacing.s4),
        verticalArrangement = Arrangement.spacedBy(Spacing.s4),
    ) {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal), color = VoraTextMuted, maxLines = 1)
        Text(value, style = MaterialTheme.typography.titleSmall, color = VoraTextPrimary, maxLines = 1)
    }
}

@Composable
private fun RecommendedWorkout(onStartWorkout: () -> Unit) {
    InfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(R.drawable.ic_calendar), contentDescription = null, tint = VoraGreen, modifier = Modifier.size(16.dp))
            Text(
                stringResource(R.string.home_recommended_workout),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.s6),
                style = MaterialTheme.typography.bodyMedium,
                color = VoraTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(stringResource(R.string.home_recommended_today), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium), color = VoraTextPrimary)
        }
        Spacer(Modifier.height(Spacing.s12))
        Text(stringResource(R.string.common_upper_body_strength), style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary)
        Spacer(Modifier.height(Spacing.titleGap))
        Text(
            stringResource(R.string.common_workout_meta, 42, pluralStringResource(R.plurals.common_exercise_count, 5, 5), stringResource(R.string.common_level_intermediate)),
            style = MaterialTheme.typography.bodyMedium,
            color = VoraTextSecondary,
        )
        Spacer(Modifier.height(Spacing.s16))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.home_estimated_volume, 2340), Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = VoraTextMuted)
            PrimaryButton(stringResource(R.string.home_start_workout), onStartWorkout, size = ButtonSize.Small)
        }
    }
}

@Preview(name = "Home", widthDp = 390, heightDp = 844)
@Preview(name = "Home chico", widthDp = 320, heightDp = 640)
@Preview(name = "Home tablet", widthDp = 800, heightDp = 1280)
@Composable
private fun HomePreview() {
    VoraCoachTheme { HomeScreen({}, {}, {}, {}, {}) }
}
