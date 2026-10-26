
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

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
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Exercise Guide",
            style = MaterialTheme.typography.headlineMedium
        )

        if (workout == null) {
            Text("Cargando entrenamiento...")
        } else if (exercise == null) {
            Text("No se encontró este ejercicio en el entrenamiento.")
        } else {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text("Grupo muscular: ${exercise.muscleGroup}")
            Text("Series: ${exercise.sets}")
            Text("Repeticiones: ${exercise.reps}")
            Text("Descanso: ${exercise.restSeconds} segundos")
            Text("Instrucciones: ${exercise.instructions}")
            Text("Consejo: ${exercise.tip}")
        }

        Button(onClick = onBack) {
            Text("Back to Workout")
        }
    }
}
