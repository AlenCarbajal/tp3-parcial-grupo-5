
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

@Composable
fun WorkoutActiveScreen(
    workout: Workout?,
    onGuideClick: (String) -> Unit,
    onFinish: () -> Unit
) {
    var currentIndex by rememberSaveable(workout?.id) {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (workout == null) {
            Text("Cargando entrenamiento...")
        } else if (workout.exercises.isEmpty()) {
            Text("Este entrenamiento no tiene ejercicios cargados.")
            Button(onClick = onFinish) {
                Text("Finish Workout")
            }
        } else {
            val exercise = workout.exercises[currentIndex]

            Text(
                text = "Active Workout",
                style = MaterialTheme.typography.headlineMedium
            )
            Text("Exercise ${currentIndex + 1} of ${workout.exercises.size}")
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text("Series: ${exercise.sets}")
            Text("Repeticiones: ${exercise.reps}")
            Text("Descanso: ${exercise.restSeconds} segundos")

            Button(onClick = { onGuideClick(exercise.id) }) {
                Text("Exercise Guide")
            }

            if (currentIndex < workout.exercises.lastIndex) {
                Button(onClick = { currentIndex++ }) {
                    Text("Next Exercise")
                }
            } else {
                Button(onClick = onFinish) {
                    Text("Finish Workout")
                }
            }
        }
    }
}
