package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WorkoutCatalogScreen(
    onWorkoutClick: (String) -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Entrenamientos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = viewModel::refreshWorkouts) {
            Text("Actualizar")
        }

        when {
            state.isLoading && state.workouts.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.error != null && state.workouts.isEmpty() -> {
                Text(state.error ?: "Ocurrió un error.")
                Button(onClick = viewModel::refreshWorkouts) {
                    Text("Reintentar")
                }
            }

            state.workouts.isEmpty() -> {
                Text("Todavía no hay entrenamientos disponibles.")
            }

            else -> {
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.workouts,
                        key = { workout -> workout.id }
                    ) { workout ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onWorkoutClick(workout.id)
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = workout.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text("Nivel: ${workout.level}")
                                Text("Duración: ${workout.durationMinutes} minutos")
                                Text("Ejercicios: ${workout.exerciseCount}")
                            }
                        }
                    }
                }
            }
        }
    }
}