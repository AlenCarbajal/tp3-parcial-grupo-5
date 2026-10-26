package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutViewModel

@Composable
fun VoraNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            PlaceholderScreen("Iniciar sesión", Routes.HOME to "Entrar") {
                navController.navigate(it)
            }
        }

        composable(Routes.SIGNUP) {
            PlaceholderScreen("Crear cuenta") {}
        }

        composable(Routes.HOME) {
            PlaceholderScreen(
                "Inicio",
                Routes.WORKOUT to "Entrenamiento",
                Routes.NUTRITION to "Nutrición",
                Routes.PROGRESS to "Progreso",
                Routes.PROFILE to "Perfil",
                Routes.COACH to "Asistente VORA",
                Routes.NOTIFICATIONS to "Notificaciones",
            ) {
                navController.navigate(it)
            }
        }

        composable(Routes.NOTIFICATIONS) {
            PlaceholderScreen("Notificaciones") {}
        }

        composable(Routes.COACH) {
            PlaceholderScreen("Asistente VORA") {}
        }

        composable(Routes.WORKOUT) {
            WorkoutCatalogScreen(
                onWorkoutClick = { workoutId ->
                    navController.navigate(Routes.workoutDetail(workoutId))
                }
            )
        }

        composable(
            route = Routes.WORKOUT_DETAIL,
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId").orEmpty()

            WorkoutDetailScreen(
                workoutId = workoutId,
                onBack = { navController.popBackStack() },
                viewModel = hiltViewModel(
                    navController.getBackStackEntry(Routes.WORKOUT)
                )
            )
        }

        composable(Routes.NUTRITION) {
            PlaceholderScreen("Nutrición") {}
        }

        composable(Routes.PROGRESS) {
            PlaceholderScreen("Progreso") {}
        }

        composable(Routes.PROFILE) {
            PlaceholderScreen("Perfil") {}
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    vararg links: Pair<String, String>,
    onNavigate: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Text(title)
        links.forEach { (route, label) ->
            Button(onClick = { onNavigate(route) }) {
                Text(label)
            }
        }
    }
}

