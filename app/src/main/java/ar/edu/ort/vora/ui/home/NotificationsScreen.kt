package ar.edu.ort.vora.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.InfoCard
import ar.edu.ort.vora.ui.components.StatusDot
import ar.edu.ort.vora.ui.components.VoraTextButton
import ar.edu.ort.vora.ui.theme.Spacing
import ar.edu.ort.vora.ui.theme.VoraCoachTheme
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraTextMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

private class Notice(
    val source: String,
    val title: String,
    val body: String,
    val time: String,
    @DrawableRes val icon: Int,
    val unread: Boolean,
    val open: () -> Unit,
)

// Notificaciones de ejemplo; cada una abre el área de la que habla.
@Composable
fun NotificationsScreen(
    onWeeklyReview: () -> Unit,
    onWorkout: () -> Unit,
    onNutrition: () -> Unit,
    onProgress: () -> Unit,
) {
    var allRead by rememberSaveable { mutableStateOf(false) }
    val today = listOf(
        Notice("VORA Coach", "Your weekly health review is ready.", "We noticed your training consistency improved by 14% over the last week.", "2h ago", R.drawable.ic_sparkles, true, onWeeklyReview),
        Notice("Workout Plan", "Upper Body Strength is ready.", "Equipped for home/gym. Approx. 42 minutes.", "4h ago", R.drawable.ic_nav_workout, true, onWorkout),
        Notice("Nutrition Goal", "Protein target milestone alert.", "You require 36g more of protein to hit today's UK performance baseline.", "6h ago", R.drawable.ic_nav_nutrition, false, onNutrition),
    )
    val yesterday = listOf(
        Notice("Milestone Reached", "4-day workout streak logged!", "Outstanding work, Alex. Let's make it 5 today.", "1d ago", R.drawable.ic_flame, false, onProgress),
    )

    TabScreen {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Notifications", Modifier.weight(1f), style = MaterialTheme.typography.headlineMedium, color = VoraTextPrimary)
            VoraTextButton("Mark all as read", onClick = { allRead = true })
        }
        Section("Today", today, allRead)
        Section("Yesterday", yesterday, allRead)
    }
}

@Composable
private fun Section(title: String, notices: List<Notice>, allRead: Boolean) {
    Text(
        title.uppercase(),
        modifier = Modifier.padding(top = Spacing.s8),
        style = MaterialTheme.typography.labelSmall,
        color = VoraTextSecondary,
    )
    notices.forEach { NoticeCard(it, unread = it.unread && !allRead) }
}

@Composable
private fun NoticeCard(notice: Notice, unread: Boolean) {
    InfoCard(
        Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = notice.open),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            Icon(
                painterResource(notice.icon),
                contentDescription = null,
                tint = VoraGreen,
                modifier = Modifier
                    .padding(top = Spacing.s4)
                    .size(16.dp),
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Spacing.s4)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(notice.source, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = VoraTextSecondary)
                    Text(notice.time, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal), color = VoraTextMuted)
                    if (unread) StatusDot(Modifier.padding(start = Spacing.s4).size(6.dp), color = VoraGreen)
                }
                Text(notice.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = VoraTextPrimary)
                Text(notice.body, style = MaterialTheme.typography.bodyMedium, color = VoraTextSecondary)
            }
        }
    }
}

@Preview(name = "Notifications", widthDp = 390, heightDp = 844)
@Composable
private fun NotificationsPreview() {
    VoraCoachTheme { NotificationsScreen({}, {}, {}, {}) }
}
