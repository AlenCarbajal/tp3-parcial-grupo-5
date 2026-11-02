
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraMint
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

@Composable
fun WorkoutCatalogScreen(
    onWorkoutClick: (String) -> Unit,
    onPlanClick: () -> Unit,
    viewModel: WorkoutViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Workouts",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VoraTextPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = viewModel::refreshWorkouts,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VoraGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Actualizar")
            }

            OutlinedButton(
                onClick = onPlanClick,
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Your Plan")
            }
        }

        when {
            state.isLoading && state.workouts.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = VoraMint
                )
            }

            state.error != null && state.workouts.isEmpty() -> {
                Text(
                    text = state.error ?: "Ocurrió un error.",
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = viewModel::refreshWorkouts,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoraGreen
                    )
                ) {
                    Text("Reintentar")
                }
            }

            state.workouts.isEmpty() -> {
                Text(
                    text = "Todavía no hay entrenamientos disponibles.",
                    color = VoraTextPrimary
                )
            }

            else -> {
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "READY FOR TODAY?",
                            style = MaterialTheme.typography.labelSmall,
                            color = VoraGreen
                        )
                    }

                    items(
                        items = state.workouts,
                        key = { workout -> workout.id }
                    ) { workout ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onWorkoutClick(workout.id)
                                },
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = VoraGreen
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = workout.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                                Text(
                                    text = "${workout.durationMinutes} min • " +
                                            "${workout.exerciseCount} ejercicios • " +
                                            "${workout.level}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = VoraMint
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Button(
                                    onClick = {
                                        onWorkoutClick(workout.id)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = VoraLime,
                                        contentColor = VoraGreen
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Ver entrenamiento",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    if (state.isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier.align(
                                    Alignment.CenterHorizontally
                                ),
                                color = VoraMint
                            )
                        }
                    }
                }
            }
        }
    }
}
