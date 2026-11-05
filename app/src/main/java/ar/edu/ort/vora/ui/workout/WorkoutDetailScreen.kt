package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.R
import ar.edu.ort.vora.ui.components.asString
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraTextPrimary


@Composable
fun WorkoutDetailScreen(
    workoutId: String,
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onExerciseClick: (String) -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Se pide una vez por apertura: al rotar no se vuelve a llamar al servidor (RF07).
    var requested by rememberSaveable(workoutId) { mutableStateOf(false) }
    LaunchedEffect(workoutId) {
        if (!requested) {
            requested = true
            viewModel.loadWorkoutDetail(workoutId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.workout_back_link),
            style = MaterialTheme.typography.bodyMedium,
            color = VoraGreen,
            modifier = Modifier
                .clickable(onClick = onBack)
                .padding(vertical = 8.dp)
        )

        when {
            state.isLoadingDetail &&
                    state.selectedWorkout?.id != workoutId -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = VoraMint
                )
            }

            state.selectedWorkout?.id == workoutId -> {
                val workout = state.selectedWorkout!!

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.workout_overview),
                            style = MaterialTheme.typography.titleMedium,
                            color = VoraGreen,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = VoraTextPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(
                                R.string.workout_detail_meta,
                                workout.durationMinutes,
                                workout.level,
                                pluralStringResource(R.plurals.common_exercise_count, workout.exerciseCount, workout.exerciseCount)
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = VoraMintLight
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.workout_coach_note),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = VoraGreen
                                )

                                Text(
                                    text = workout.description
                                        ?.takeIf { it.isNotBlank() }
                                        ?: stringResource(R.string.workout_coach_note_default),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = VoraTextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.workout_exercises_header),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = VoraGreen
                        )
                    }

                    itemsIndexed(
                        items = workout.exercises,
                        key = { _, exercise -> exercise.id }
                    ) { index, exercise ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = VoraMintLight
                                    )
                                ) {
                                    Text(
                                        text = "%02d".format(index + 1),
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 8.dp
                                        ),
                                        color = VoraGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = VoraTextPrimary
                                    )

                                    Text(
                                        text = stringResource(
                                            R.string.workout_sets_reps_rest,
                                            exercise.sets,
                                            exercise.reps,
                                            exercise.restSeconds
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )

                                    Text(
                                        text = exercise.muscleGroup,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = VoraGreen
                                    )
                                }

                                OutlinedButton(
                                    onClick = {
                                        onExerciseClick(exercise.id)
                                    },
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                        horizontal = 10.dp,
                                        vertical = 0.dp
                                    )
                                ) {
                                    Text(stringResource(R.string.common_view))
                                }
                            }
                        }
                    }

                    if (state.detailError != null) {
                        item {
                            Text(
                                text = state.detailError!!.asString(),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onStartWorkout,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VoraGreen,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.common_start_workout))
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_back_to_catalog),
                            color = VoraGreen
                        )
                    }
                }
            }

            state.detailError != null -> {
                Text(
                    text = state.detailError!!.asString(),
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = { viewModel.loadWorkoutDetail(workoutId) }
                ) {
                    Text(stringResource(R.string.common_retry))
                }
            }

            else -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = VoraMint
                )
            }
        }
    }
}


