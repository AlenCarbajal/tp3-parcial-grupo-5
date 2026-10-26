
package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.ort.vora.progress.ProgressDashboardScreen
import ar.edu.ort.vora.progress.ProgressScreen
import ar.edu.ort.vora.ui.components.VoraBottomBar
import ar.edu.ort.vora.ui.workout.ExerciseGuideScreen
import ar.edu.ort.vora.ui.workout.WorkoutActiveScreen
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutCompletedScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutPlanScreen
import ar.edu.ort.vora.ui.workout.WorkoutPrepScreen
import ar.edu.ort.vora.ui.workout.WorkoutViewModel

private val tabRoutes = listOf(
    Routes.HOME,
    Routes.WORKOUT,
    Routes.NUTRITION,
    Routes.PROGRESS,
    Routes.PROFILE,
)

private val routesWithBottomBar = setOf(
    Routes.HOME,
    Routes.WORKOUT,
    Routes.NUTRITION,
    Routes.PROGRESS,
    Routes.PROFILE,
    Routes.NOTIFICATIONS,
    Routes.NUTRITION_MEALS,
    Routes.NUTRITION_PHOTO,
    Routes.NUTRITION_REVIEW,
    Routes.NUTRITION_MEAL,
    Routes.NUTRITION_ADD,
    Routes.NUTRITION_VOICE,
    Routes.NUTRITION_ANALYZING,
    Routes.NUTRITION_MANUAL,
    Routes.PROGRESS_TODAY,
    Routes.PROGRESS_WORKOUT,
    Routes.PROGRESS_NUTRITION,
    Routes.PROGRESS_HEALTH,
    Routes.PROGRESS_WEEKLY,
)

@Composable
fun VoraNavHost(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VoraBottomBar(
                    selectedIndex = tabRoutes.indexOfFirst {
                        currentRoute?.startsWith(it) == true
                    }.coerceAtLeast(0),
                    onSelect = { index ->
                        navController.navigate(tabRoutes[index]) {
                            popUpTo(Routes.HOME) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(padding)
        ) {
            // Acceso
            composable(Routes.LOGIN) {
                PlaceholderScreen(
                    title = "Iniciar sesión",
                    links = arrayOf(
                        Routes.HOME to "Entrar",
                        Routes.SIGNUP to "Crear cuenta"
                    )
                ) { route ->
                    if (route == Routes.HOME) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(route)
                    }
                }
            }

            composable(Routes.SIGNUP) {
                PlaceholderScreen(
                    "Crear cuenta",
                    Routes.HOME to "Registrarme",
                    Routes.LOGIN to "Iniciar sesión"
                ) { route ->
                    navController.navigate(route)
                }
            }

            // Inicio
            composable(Routes.HOME) {
                PlaceholderScreen(
                    "Inicio",
                    Routes.NOTIFICATIONS to "Notificaciones",
                    Routes.COACH to "Asistente VORA",
                    Routes.WORKOUT to "Entrenamiento",
                    Routes.NUTRITION_ADD to "Registrar comida",
                    Routes.PROGRESS_TODAY to "Resumen de hoy"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NOTIFICATIONS) {
                PlaceholderScreen(
                    "Notificaciones",
                    Routes.PROGRESS_WEEKLY to "Resumen semanal"
                ) { route ->
                    navController.navigate(route)
                }
            }

            // Asistente VORA
            composable(Routes.COACH) {
                PlaceholderScreen(
                    "Asistente VORA",
                    Routes.COACH_LISTENING to "Hablar",
                    Routes.COACH_CHAT to "Escribir"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.COACH_LISTENING) {
                PlaceholderScreen(
                    "Escuchando",
                    Routes.COACH_PROCESSING to "Continuar"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.COACH_PROCESSING) {
                PlaceholderScreen(
                    "Procesando",
                    Routes.COACH_SPEAKING to "Continuar"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.COACH_SPEAKING) {
                PlaceholderScreen(
                    "VORA está hablando",
                    Routes.COACH_CHAT to "Interrumpir"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.COACH_CHAT) {
                PlaceholderScreen("Conversación") {}
            }

            composable(Routes.COACH_ERROR) {
                PlaceholderScreen(
                    "Error del asistente",
                    Routes.COACH_LISTENING to "Intentar de nuevo",
                    Routes.COACH_CHAT to "Escribir"
                ) { route ->
                    navController.navigate(route)
                }
            }

            // Entrenamiento: conserva las pantallas reales de Workout.
            composable(Routes.WORKOUT) {
                WorkoutCatalogScreen(
                    onWorkoutClick = { workoutId ->
                        navController.navigate(
                            Routes.workoutDetail(workoutId)
                        )
                    },
                    onPlanClick = {
                        navController.navigate(Routes.WORKOUT_PLAN)
                    }
                )
            }

            composable(Routes.WORKOUT_PLAN) {
                val workoutEntry = rememberWorkoutEntry(navController)
                val viewModel = hiltViewModel<WorkoutViewModel>(workoutEntry)
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                WorkoutPlanScreen(
                    workouts = state.workouts,
                    onWorkoutClick = { workoutId ->
                        navController.navigate(
                            Routes.workoutDetail(workoutId)
                        )
                    },
                    onStartWorkout = { workoutId ->
                        navController.navigate(
                            Routes.workoutPrep(workoutId)
                        )
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.WORKOUT_DETAIL,
                arguments = listOf(
                    navArgument("workoutId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val workoutId =
                    entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(
                    rememberWorkoutEntry(navController)
                )

                WorkoutDetailScreen(
                    workoutId = workoutId,
                    onBack = { navController.popBackStack() },
                    onStartWorkout = {
                        navController.navigate(
                            Routes.workoutPrep(workoutId)
                        )
                    },
                    onExerciseClick = { exerciseId ->
                        navController.navigate(
                            Routes.workoutGuide(workoutId, exerciseId)
                        )
                    },
                    viewModel = viewModel
                )
            }

            composable(
                route = Routes.WORKOUT_PREP,
                arguments = listOf(
                    navArgument("workoutId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val workoutId =
                    entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(
                    rememberWorkoutEntry(navController)
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
                        navController.navigate(
                            Routes.workoutActive(workoutId)
                        )
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.WORKOUT_ACTIVE,
                arguments = listOf(
                    navArgument("workoutId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val workoutId =
                    entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(
                    rememberWorkoutEntry(navController)
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
                        navController.navigate(
                            Routes.workoutDone(workoutId)
                        )
                    }
                )
            }

            composable(
                route = Routes.WORKOUT_GUIDE,
                arguments = listOf(
                    navArgument("workoutId") {
                        type = NavType.StringType
                    },
                    navArgument("exerciseId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val workoutId =
                    entry.arguments?.getString("workoutId").orEmpty()
                val exerciseId =
                    entry.arguments?.getString("exerciseId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(
                    rememberWorkoutEntry(navController)
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
                    navArgument("workoutId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val workoutId =
                    entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(
                    rememberWorkoutEntry(navController)
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
                        navController.navigate(
                            Routes.workoutDetail(workoutId)
                        ) {
                            popUpTo(Routes.WORKOUT) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Nutrición
            composable(Routes.NUTRITION) {
                PlaceholderScreen(
                    "Nutrición",
                    Routes.NUTRITION_MEALS to "Comidas de hoy",
                    Routes.NUTRITION_VOICE to "Registrar por voz",
                    Routes.NUTRITION_PHOTO to "Registrar con foto",
                    Routes.NUTRITION_MANUAL to "Buscar alimento"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NUTRITION_MEALS) {
                PlaceholderScreen(
                    "Comidas de hoy",
                    Routes.NUTRITION_ADD to "Agregar comida",
                    Routes.nutritionMeal("1") to "Ver detalle"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NUTRITION_ADD) {
                PlaceholderScreen("Agregar comida") {}
            }

            composable(Routes.NUTRITION_VOICE) {
                PlaceholderScreen(
                    "Registro por voz",
                    Routes.NUTRITION_ANALYZING to "Continuar"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NUTRITION_ANALYZING) {
                PlaceholderScreen(
                    "Analizando alimento",
                    Routes.NUTRITION_REVIEW to "Ver resultado"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NUTRITION_PHOTO) {
                PlaceholderScreen(
                    "Registrar con foto",
                    Routes.NUTRITION_REVIEW to "Revisar"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.NUTRITION_REVIEW) {
                PlaceholderScreen(
                    "Revisar alimento",
                    Routes.NUTRITION_MEALS to "Guardar"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(
                route = Routes.NUTRITION_MEAL,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val id = entry.arguments?.getString("id").orEmpty()
                PlaceholderScreen("Detalle de comida $id") {}
            }

            composable(Routes.NUTRITION_MANUAL) {
                PlaceholderScreen("Buscar alimento") {}
            }

            // Progreso
            composable(Routes.PROGRESS) {
                ProgressDashboardScreen()
            }

            composable(Routes.PROGRESS_TODAY) {
                ProgressScreen()
            }

            composable(Routes.PROGRESS_WORKOUT) {
                PlaceholderScreen("Progreso de entrenamiento") {}
            }

            composable(Routes.PROGRESS_NUTRITION) {
                PlaceholderScreen("Progreso de nutrición") {}
            }

            composable(Routes.PROGRESS_HEALTH) {
                PlaceholderScreen("Tendencias de salud") {}
            }

            composable(Routes.PROGRESS_WEEKLY) {
                PlaceholderScreen(
                    "Resumen semanal",
                    Routes.COACH to "Consultar a VORA"
                ) { route ->
                    navController.navigate(route)
                }
            }

            // Perfil
            composable(Routes.PROFILE) {
                PlaceholderScreen(
                    "Perfil",
                    Routes.PROFILE_EDIT to "Editar perfil",
                    Routes.PROFILE_GOALS to "Objetivos",
                    Routes.PROFILE_NUTRITION to "Preferencias de nutrición",
                    Routes.PROFILE_SETTINGS to "Configuración"
                ) { route ->
                    navController.navigate(route)
                }
            }

            composable(Routes.PROFILE_EDIT) {
                PlaceholderScreen("Editar perfil") {}
            }

            composable(Routes.PROFILE_GOALS) {
                PlaceholderScreen("Objetivos") {}
            }

            composable(Routes.PROFILE_NUTRITION) {
                PlaceholderScreen("Preferencias de nutrición") {}
            }

            composable(Routes.PROFILE_VOICE) {
                PlaceholderScreen("Preferencias de voz") {}
            }

            composable(Routes.PROFILE_SETTINGS) {
                PlaceholderScreen("Configuración") {}
            }
        }
    }
}

@Composable
private fun rememberWorkoutEntry(
    navController: NavHostController
): androidx.navigation.NavBackStackEntry {
    val entry = navController.currentBackStackEntry
    return androidx.compose.runtime.remember(entry) {
        navController.getBackStackEntry(Routes.WORKOUT)
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    vararg links: Pair<String, String>,
    onNavigate: (String) -> Unit
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
