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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val DetailDarkGreen = Color(0xFF123F35)
private val DetailMint = Color(0xFF60D5B1)
private val DetailLime = Color(0xFFD0EE69)
private val DetailBackground = Color(0xFFF5F7F4)
private val DetailText = Color(0xFF18352E)

@Composable
fun WorkoutDetailScreen(
    workoutId: String,
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onExerciseClick: (String) -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(workoutId) {
        viewModel.loadWorkoutDetail(workoutId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = "‹  Volver",
            style = MaterialTheme.typography.bodyMedium,
            color = DetailDarkGreen,
            modifier = Modifier
                .clickable(onClick = onBack)
                .padding(vertical = 8.dp)
        )

        when {
            state.isLoadingDetail &&
                    state.selectedWorkout?.id != workoutId -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = DetailMint
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
                            text = "Workout Overview",
                            style = MaterialTheme.typography.titleMedium,
                            color = DetailDarkGreen,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = DetailText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${workout.durationMinutes} min  •  " +
                                    "${workout.level}  •  " +
                                    "${workout.exerciseCount} ejercicios",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE2F4EA)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "●  VORA COACH NOTE",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = DetailDarkGreen
                                )

                                Text(
                                    text = workout.description
                                        ?.takeIf { it.isNotBlank() }
                                        ?: "Mantené una técnica controlada y priorizá " +
                                        "la calidad de cada repetición.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = DetailText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "EJERCICIOS",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = DetailDarkGreen
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
                                        containerColor = Color(0xFFE2F4EA)
                                    )
                                ) {
                                    Text(
                                        text = "%02d".format(index + 1),
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 8.dp
                                        ),
                                        color = DetailDarkGreen,
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
                                        color = DetailText
                                    )

                                    Text(
                                        text = "${exercise.sets} × ${exercise.reps} " +
                                                "• Descanso ${exercise.restSeconds}s",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )

                                    Text(
                                        text = exercise.muscleGroup,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = DetailDarkGreen
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
                                    Text("Ver")
                                }
                            }
                        }
                    }

                    if (state.detailError != null) {
                        item {
                            Text(
                                text = state.detailError!!,
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
                            containerColor = DetailDarkGreen,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Start Workout")
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Volver al catálogo",
                            color = DetailDarkGreen
                        )
                    }
                }
            }

            state.detailError != null -> {
                Text(
                    text = state.detailError!!,
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = { viewModel.loadWorkoutDetail(workoutId) }
                ) {
                    Text("Reintentar")
                }
            }

            else -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = DetailMint
                )
            }
        }
    }
}


