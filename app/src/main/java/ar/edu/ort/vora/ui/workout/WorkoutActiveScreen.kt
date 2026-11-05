package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Workout
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraGreenRaised
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary


@Composable
fun WorkoutActiveScreen(
    workout: Workout?,
    onGuideClick: (String) -> Unit,
    onFinish: () -> Unit
) {
    var currentIndex by rememberSaveable(workout?.id) {
        mutableIntStateOf(0)
    }

    WorkoutScreenColumn(
        modifier = Modifier
            .background(VoraBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        if (workout == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.workout_loading),
                    color = VoraTextPrimary
                )
            }
        } else if (workout.exercises.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.workout_active_no_exercises),
                        style = MaterialTheme.typography.titleLarge,
                        color = VoraTextPrimary
                    )

                    Button(
                        onClick = onFinish,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VoraGreen
                        )
                    ) {
                        Text(stringResource(R.string.workout_finish))
                    }
                }
            }
        } else {
            val exercise = workout.exercises[
                currentIndex.coerceIn(workout.exercises.indices)
            ]

            Text(
                text = stringResource(R.string.workout_your_workout),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = VoraGreen
            )

            Text(
                text = stringResource(R.string.workout_active_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = VoraTextPrimary
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = VoraGreen
                    )

                    Text(
                        text = "${currentIndex + 1}/${workout.exercises.size}",
                        color = VoraGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = VoraBorder,
                            shape = CircleShape
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                (currentIndex + 1).toFloat() /
                                        workout.exercises.size
                            )
                            .height(8.dp)
                            .background(
                                color = VoraMint,
                                shape = CircleShape
                            )
                    )
                }

                Text(
                    text = stringResource(
                        R.string.workout_active_progress,
                        currentIndex + 1,
                        workout.exercises.size
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = VoraGreen
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = VoraGreenRaised,
                                shape = CircleShape
                            )
                            .padding(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_active_exercise_badge, currentIndex + 1),
                            color = VoraMint,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = exercise.muscleGroup,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VoraMint,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ActiveStat(
                            value = "${exercise.sets}",
                            label = stringResource(R.string.workout_sets_caps),
                            modifier = Modifier.weight(1f)
                        )

                        ActiveStat(
                            value = "${exercise.reps}",
                            label = stringResource(R.string.workout_reps_caps),
                            modifier = Modifier.weight(1f)
                        )

                        ActiveStat(
                            value = stringResource(R.string.workout_seconds, exercise.restSeconds),
                            label = stringResource(R.string.workout_rest_caps),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    OutlinedButton(
                        onClick = { onGuideClick(exercise.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_guide_title),
                            color = Color.White
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (currentIndex < workout.exercises.lastIndex) {
                        currentIndex++
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VoraLime,
                    contentColor = VoraGreen
                )
            ) {
                Text(
                    text = if (currentIndex < workout.exercises.lastIndex) {
                        stringResource(R.string.workout_next_exercise)
                    } else {
                        stringResource(R.string.workout_finish_check)
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ActiveStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = VoraGreenRaised,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(vertical = 14.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = VoraMint,
            textAlign = TextAlign.Center
        )
    }
}
