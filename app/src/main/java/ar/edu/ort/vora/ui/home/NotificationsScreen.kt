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
import androidx.compose.ui.res.stringResource
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
        Notice(stringResource(R.string.coach_title), stringResource(R.string.home_notice_review_title), stringResource(R.string.home_notice_review_body), stringResource(R.string.home_hours_ago, 2), R.drawable.ic_sparkles, true, onWeeklyReview),
        Notice(stringResource(R.string.home_notice_workout_source), stringResource(R.string.home_notice_workout_title), stringResource(R.string.home_notice_workout_body), stringResource(R.string.home_hours_ago, 4), R.drawable.ic_nav_workout, true, onWorkout),
        Notice(stringResource(R.string.home_notice_nutrition_source), stringResource(R.string.home_notice_nutrition_title), stringResource(R.string.home_notice_nutrition_body), stringResource(R.string.home_hours_ago, 6), R.drawable.ic_nav_nutrition, false, onNutrition),
    )
    val yesterday = listOf(
        Notice(stringResource(R.string.home_notice_streak_source), stringResource(R.string.home_notice_streak_title), stringResource(R.string.home_notice_streak_body), stringResource(R.string.home_days_ago, 1), R.drawable.ic_flame, false, onProgress),
    )

    TabScreen {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.common_notifications), Modifier.weight(1f), style = MaterialTheme.typography.headlineMedium, color = VoraTextPrimary)
            VoraTextButton(stringResource(R.string.home_mark_all_read), onClick = { allRead = true })
        }
        Section(stringResource(R.string.common_today), today, allRead)
        Section(stringResource(R.string.common_yesterday), yesterday, allRead)
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
