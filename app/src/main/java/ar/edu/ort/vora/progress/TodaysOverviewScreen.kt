
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraError
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary


@Composable
fun TodaysOverviewScreen(
    onStartWorkout: () -> Unit = {},
    onOpenDashboard: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoraBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(R.string.progress_today_date),
                fontSize = 12.sp,
                color = VoraTextSecondary,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = stringResource(R.string.common_todays_overview),
                fontSize = 27.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = VoraTextPrimary
            )
        }

        // Cumplimiento diario
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
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.progress_daily_completion),
                            fontSize = 11.sp,
                            color = VoraTextSecondary
                        )

                        Text(
                            text = "72%",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = VoraTextPrimary
                        )
                    }

                    CircularCompletion(
                        progress = 0.72f,
                        modifier = Modifier.size(54.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(VoraBorder)
                )

                CompletionRow(
                    color = VoraError,
                    title = stringResource(R.string.progress_workout_progress),
                    detail = stringResource(R.string.progress_not_started),
                    percent = "0%"
                )

                CompletionRow(
                    color = VoraMint,
                    title = stringResource(R.string.progress_nutrition_target),
                    detail = stringResource(R.string.common_value_kcal_of, 1420, 2100),
                    percent = "78%"
                )

                CompletionRow(
                    color = VoraGreen,
                    title = stringResource(R.string.progress_activity_target),
                    detail = pluralStringResource(R.plurals.progress_steps_count, 7842, stringResource(R.string.common_number, 7842)),
                    percent = "84%"
                )

                CompletionRow(
                    color = VoraLime,
                    title = stringResource(R.string.progress_hydration_intake),
                    detail = stringResource(R.string.progress_hydration_detail),
                    percent = "65%"
                )
            }
        }

        // VORA Advice
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = VoraGreen
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "✣  " + stringResource(R.string.progress_vora_advice),
                    color = VoraMint,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = stringResource(R.string.progress_today_advice),
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        }

        // Desglose por categorías
        Text(
            text = stringResource(R.string.progress_category_breakdowns),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = VoraTextPrimary
        )

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
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.progress_workout_caps),
                        color = VoraTextSecondary,
                        fontSize = 10.sp
                    )

                    Text(
                        text = stringResource(R.string.progress_not_started_caps),
                        color = VoraError,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = stringResource(R.string.common_upper_body_strength),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = VoraTextPrimary
                )

                Text(
                    text = stringResource(
                        R.string.common_workout_meta,
                        42,
                        pluralStringResource(R.plurals.common_exercise_count, 5, 5),
                        stringResource(R.string.common_level_intermediate),
                    ),
                    fontSize = 11.sp,
                    color = VoraTextSecondary
                )

                Button(
                    onClick = onStartWorkout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoraGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.common_start_workout),
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Objetivo actual y objetivo semanal
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.progress_current_goal),
                        fontSize = 9.sp,
                        color = VoraTextSecondary
                    )

                    Text(
                        text = stringResource(R.string.common_goal_strength),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VoraTextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(VoraBorder)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.progress_weekly_target),
                        fontSize = 9.sp,
                        color = VoraTextSecondary
                    )

                    Text(
                        text = stringResource(R.string.progress_weekly_target_value),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = VoraTextPrimary
                    )
                }
            }
        }

        // Acceso al dashboard general
        Button(
            onClick = onOpenDashboard,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VoraGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.progress_view_dashboard),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun CompletionRow(
    color: Color,
    title: String,
    detail: String,
    percent: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .background(color, CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = title,
            fontSize = 11.sp,
            color = VoraTextPrimary,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = detail,
            fontSize = 10.sp,
            color = VoraTextSecondary
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = percent,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = VoraTextPrimary
        )
    }
}

@Composable
private fun CircularCompletion(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.drawBehind {
            val stroke = 4.dp.toPx()
            val diameter = size.minDimension - stroke

            val topLeft = Offset(
                (size.width - diameter) / 2,
                (size.height - diameter) / 2
            )

            drawArc(
                color = VoraBackground,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = stroke,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = VoraGreen,
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = stroke,
                    cap = StrokeCap.Round
                )
            )
        }
    )
}
