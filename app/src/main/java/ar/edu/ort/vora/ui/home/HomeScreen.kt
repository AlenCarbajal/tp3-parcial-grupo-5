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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        CoachNote("You're on track. A protein-rich dinner would help you reach your daily target.")
        Text(
            "Today's Overview",
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
            Text("AM", style = MaterialTheme.typography.labelLarge, color = VoraGreen)
        }
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = Spacing.s12),
        ) {
            Text("Good morning", style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            Text("Alex Morgan", style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Box {
            IconCircleButton(R.drawable.ic_bell, "Notificaciones", onNotifications)
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
                "VORA Coach",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.s8),
                style = MaterialTheme.typography.bodyLarge,
                color = VoraMint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            StatusTag("AI online", dark = true)
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Text("\"How can I support your fitness targets today?\"", style = PanelTitleStyle)
            Text(
                "Tap the mic to talk naturally. I'll plan or log for you.",
                style = MaterialTheme.typography.bodySmall,
                color = VoraSurface.copy(alpha = 0.6f),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            LimeButton("Start voice session", onVoiceSession, Modifier.fillMaxWidth(), icon = R.drawable.ic_mic)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s8), verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
                PanelButton("Start my workout", onStartWorkout, size = ButtonSize.Small)
                PanelButton("Log breakfast", onLogMeal, size = ButtonSize.Small)
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
        InfoCard(Modifier.weight(0.36f).fillMaxHeight()) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                ProgressRing(0.72f) {
                    Text("72%", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VoraTextPrimary)
                }
                Spacer(Modifier.height(Spacing.s8))
                Text("Daily Goal", style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
            }
        }
        InfoCard(Modifier.weight(0.64f).fillMaxHeight()) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Nutrition", Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = VoraTextPrimary)) { append("1,420") }
                            append(" / 2,100 kcal")
                        },
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                        color = VoraTextMuted,
                    )
                }
                Spacer(Modifier.height(Spacing.s10))
                MacroProgress("Protein", "104g / 140g", 104 / 140f, VoraGreen)
                Spacer(Modifier.height(Spacing.s8))
                MacroProgress("Carbs", "152g / 220g", 152 / 220f, VoraMint)
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
            Stat("Steps", "7,842")
            VerticalDivider(color = VoraBorder)
            Stat("Distance", "5.6 km")
            VerticalDivider(color = VoraBorder)
            Stat("Active kcal", "312 kcal")
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
        Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VoraTextPrimary, maxLines = 1)
    }
}

@Composable
private fun RecommendedWorkout(onStartWorkout: () -> Unit) {
    InfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(R.drawable.ic_calendar), contentDescription = null, tint = VoraGreen, modifier = Modifier.size(14.dp))
            Text(
                "RECOMMENDED WORKOUT",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.s6),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                color = VoraTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text("TODAY", style = MaterialTheme.typography.labelSmall, color = VoraGreen)
        }
        Spacer(Modifier.height(Spacing.s12))
        Text("Upper Body Strength", style = MaterialTheme.typography.titleMedium, color = VoraTextPrimary)
        Spacer(Modifier.height(Spacing.titleGap))
        Text("42 min • 5 exercises • Intermediate", style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
        Spacer(Modifier.height(Spacing.s16))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Estimated Vol: 2,340 kg", Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = VoraTextMuted)
            PrimaryButton("Start workout", onStartWorkout, size = ButtonSize.Small)
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
