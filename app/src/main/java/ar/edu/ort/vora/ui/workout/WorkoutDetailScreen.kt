package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WorkoutDetailScreen(
    workoutId: String,
    onBack: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(workoutId) {
        viewModel.loadWorkoutDetail(workoutId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        when {
            state.isLoadingDetail && state.selectedWorkout?.id != workoutId -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.selectedWorkout?.id == workoutId -> {
                val workout = state.selectedWorkout!!

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text("Nivel: ${workout.level}")
                        Text("Duración: ${workout.durationMinutes} minutos")
                        Text("Ejercicios: ${workout.exerciseCount}")

                        workout.description?.let { description ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(description)
                        }

                        if (state.detailError != null) {
                            Text(
                                text = state.detailError!!,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    items(
                        items = workout.exercises,
                        key = { exercise -> exercise.id }
                    ) { exercise ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = exercise.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Grupo muscular: ${exercise.muscleGroup}")
                                Text("Series: ${exercise.sets}")
                                Text("Repeticiones: ${exercise.reps}")
                                Text("Descanso: ${exercise.restSeconds} segundos")

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(exercise.instructions)
                                Text("Consejo: ${exercise.tip}")
                            }
                        }
                    }
                }
            }

            state.detailError != null -> {
                Text(state.detailError!!)
                Button(onClick = { viewModel.loadWorkoutDetail(workoutId) }) {
                    Text("Reintentar")
                }
            }

            else -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}