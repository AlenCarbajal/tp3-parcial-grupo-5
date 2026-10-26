package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.ort.vora.ui.workout.ExerciseGuideScreen
import ar.edu.ort.vora.ui.workout.WorkoutActiveScreen
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutCompletedScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutPlanScreen
import ar.edu.ort.vora.ui.workout.WorkoutPrepScreen
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
                },
                onPlanClick = {
                    navController.navigate(Routes.WORKOUT_PLAN)
                }
            )
        }

        // Your Plan: ruta fija antes de las rutas con parámetros.
        composable(Routes.WORKOUT_PLAN) {
            val viewModel = hiltViewModel<WorkoutViewModel>(
                navController.getBackStackEntry(Routes.WORKOUT)
            )
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            WorkoutPlanScreen(
                workouts = state.workouts,
                onWorkoutClick = { workoutId ->
                    navController.navigate(Routes.workoutDetail(workoutId))
                },
                onStartWorkout = { workoutId ->
                    navController.navigate(Routes.workoutPrep(workoutId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.WORKOUT_DETAIL,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val workoutId =
                backStackEntry.arguments?.getString("workoutId").orEmpty()

            WorkoutDetailScreen(
                workoutId = workoutId,
                onBack = { navController.popBackStack() },
                onStartWorkout = {
                    navController.navigate(Routes.workoutPrep(workoutId))
                },
                onExerciseClick = { exerciseId ->
                    navController.navigate(
                        Routes.workoutGuide(workoutId, exerciseId)
                    )
                },
                viewModel = hiltViewModel(
                    navController.getBackStackEntry(Routes.WORKOUT)
                )
            )
        }

        composable(
            route = Routes.WORKOUT_PREP,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val workoutId =
                backStackEntry.arguments?.getString("workoutId").orEmpty()
            val viewModel = hiltViewModel<WorkoutViewModel>(
                navController.getBackStackEntry(Routes.WORKOUT)
            )
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(workoutId) {
                if (state.selectedWorkout?.id != workoutId) {
                    viewModel.loadWorkoutDetail(workoutId)
                }
            }

            WorkoutPrepScreen(
                workout = state.selectedWorkout
                    ?.takeIf { it.id == workoutId },
                onStartWorkout = {
                    navController.navigate(Routes.workoutActive(workoutId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.WORKOUT_ACTIVE,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val workoutId =
                backStackEntry.arguments?.getString("workoutId").orEmpty()
            val viewModel = hiltViewModel<WorkoutViewModel>(
                navController.getBackStackEntry(Routes.WORKOUT)
            )
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(workoutId) {
                if (state.selectedWorkout?.id != workoutId) {
                    viewModel.loadWorkoutDetail(workoutId)
                }
            }

            WorkoutActiveScreen(
                workout = state.selectedWorkout
                    ?.takeIf { it.id == workoutId },
                onGuideClick = { exerciseId ->
                    navController.navigate(
                        Routes.workoutGuide(workoutId, exerciseId)
                    )
                },
                onFinish = {
                    navController.navigate(Routes.workoutDone(workoutId))
                }
            )
        }

        composable(
            route = Routes.WORKOUT_GUIDE,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType },
                navArgument("exerciseId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val workoutId =
                backStackEntry.arguments?.getString("workoutId").orEmpty()
            val exerciseId =
                backStackEntry.arguments?.getString("exerciseId").orEmpty()
            val viewModel = hiltViewModel<WorkoutViewModel>(
                navController.getBackStackEntry(Routes.WORKOUT)
            )
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(workoutId) {
                if (state.selectedWorkout?.id != workoutId) {
                    viewModel.loadWorkoutDetail(workoutId)
                }
            }

            ExerciseGuideScreen(
                workout = state.selectedWorkout
                    ?.takeIf { it.id == workoutId },
                exerciseId = exerciseId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.WORKOUT_DONE,
            arguments = listOf(
                navArgument("workoutId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val workoutId =
                backStackEntry.arguments?.getString("workoutId").orEmpty()
            val viewModel = hiltViewModel<WorkoutViewModel>(
                navController.getBackStackEntry(Routes.WORKOUT)
            )
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(workoutId) {
                if (state.selectedWorkout?.id != workoutId) {
                    viewModel.loadWorkoutDetail(workoutId)
                }
            }

            WorkoutCompletedScreen(
                workout = state.selectedWorkout
                    ?.takeIf { it.id == workoutId },
                onHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onBackToWorkout = {
                    navController.navigate(Routes.workoutDetail(workoutId)) {
                        popUpTo(Routes.WORKOUT) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
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

