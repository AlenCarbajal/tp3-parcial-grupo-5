
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Workout

private val GuideDarkGreen = Color(0xFF123F35)
private val GuideMint = Color(0xFF60D5B1)
private val GuideLime = Color(0xFFD0EE69)
private val GuideBackground = Color(0xFFF5F7F4)
private val GuideText = Color(0xFF18352E)

@Composable
fun ExerciseGuideScreen(
    workout: Workout?,
    exerciseId: String,
    onBack: () -> Unit
) {
    val exercise = workout?.exercises?.find { it.id == exerciseId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = stringResource(R.string.workout_back_to_workout_link),
            color = GuideDarkGreen,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        )

        Text(
            text = stringResource(R.string.workout_guide_label),
            color = GuideDarkGreen,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.workout_guide_title),
            color = GuideText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        when {
            workout == null -> {
                Text(
                    text = stringResource(R.string.workout_loading),
                    color = GuideText
                )
            }

            exercise == null -> {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_guide_not_found_title),
                            color = GuideDarkGreen,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.workout_guide_not_found_body),
                            color = GuideText
                        )
                    }
                }
            }

            else -> {
                Text(
                    text = exercise.name,
                    color = GuideDarkGreen,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GuideDarkGreen
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_guide_details),
                            color = GuideMint,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(R.string.workout_guide_muscle_group),
                            color = GuideMint,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = exercise.muscleGroup,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            GuideStat(
                                label = stringResource(R.string.workout_sets),
                                value = exercise.sets.toString(),
                                modifier = Modifier.weight(1f)
                            )
                            GuideStat(
                                label = stringResource(R.string.workout_reps),
                                value = exercise.reps.toString(),
                                modifier = Modifier.weight(1f)
                            )
                            GuideStat(
                                label = stringResource(R.string.workout_rest),
                                value = stringResource(R.string.workout_seconds, exercise.restSeconds),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_guide_how_to),
                            color = GuideDarkGreen,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = exercise.instructions,
                            color = GuideText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GuideLime
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_guide_tip),
                            color = GuideDarkGreen,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = exercise.tip,
                            color = GuideDarkGreen,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GuideDarkGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.workout_back_to_workout),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun GuideStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            color = GuideMint,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
