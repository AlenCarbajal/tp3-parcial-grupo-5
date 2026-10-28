package ar.edu.ort.vora.navigation

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.ort.vora.ui.components.VoraBottomBar
import ar.edu.ort.vora.ui.workout.ExerciseGuideScreen
import ar.edu.ort.vora.ui.workout.WorkoutActiveScreen
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutCompletedScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutPlanScreen
import ar.edu.ort.vora.ui.workout.WorkoutPrepScreen
import ar.edu.ort.vora.ui.workout.WorkoutViewModel

// Rutas quemadas como texto para evitar errores si no están en tu Routes.kt
private const val ROUTE_COACH = "coach"
private const val ROUTE_COACH_LISTENING = "coach_listening"
private const val ROUTE_COACH_PROCESSING = "coach_processing"
private const val ROUTE_COACH_SPEAKING = "coach_speaking"
private const val ROUTE_COACH_CHAT = "coach_chat"
private const val ROUTE_COACH_ERROR = "coach_error"

private const val ROUTE_PROFILE = "profile"
private const val ROUTE_PROFILE_EDIT = "profile_edit"
private const val ROUTE_PROFILE_GOALS = "profile_goals"
private const val ROUTE_PROFILE_NUTRITION = "profile_nutrition"
private const val ROUTE_PROFILE_VOICE = "profile_voice"
private const val ROUTE_PROFILE_SETTINGS = "profile_settings"

private const val ROUTE_PROGRESS = "progress"
private const val ROUTE_PROGRESS_TODAY = "progress_today"
private const val ROUTE_PROGRESS_WORKOUT = "progress_workout"
private const val ROUTE_PROGRESS_NUTRITION = "progress_nutrition"
private const val ROUTE_PROGRESS_HEALTH = "progress_health"
private const val ROUTE_PROGRESS_WEEKLY = "progress_weekly"

private const val ROUTE_NUTRITION = "nutrition"
private const val ROUTE_NUTRITION_MEALS = "nutrition_meals"
private const val ROUTE_NUTRITION_ADD = "nutrition_add"
private const val ROUTE_NUTRITION_VOICE = "nutrition_voice"
private const val ROUTE_NUTRITION_ANALYZING = "nutrition_analyzing"
private const val ROUTE_NUTRITION_PHOTO = "nutrition_photo"
private const val ROUTE_NUTRITION_REVIEW = "nutrition_review"
private const val ROUTE_NUTRITION_MANUAL = "nutrition_manual"

private val bottomTabs = listOf(Routes.HOME, Routes.WORKOUT, ROUTE_NUTRITION, ROUTE_PROGRESS, ROUTE_PROFILE)

private val edgeToEdgeRoutes = setOf(
    Routes.LOGIN, Routes.SIGNUP,
    ROUTE_COACH, ROUTE_COACH_LISTENING, ROUTE_COACH_PROCESSING, ROUTE_COACH_SPEAKING, ROUTE_COACH_CHAT, ROUTE_COACH_ERROR,
)

private val darkRoutes = setOf(ROUTE_COACH_LISTENING, ROUTE_COACH_PROCESSING, ROUTE_COACH_SPEAKING)

private val routesWithBottomBar = setOf(
    Routes.HOME, Routes.WORKOUT, ROUTE_NUTRITION, ROUTE_PROGRESS, ROUTE_PROFILE,
    Routes.NOTIFICATIONS, ROUTE_NUTRITION_MEALS, ROUTE_NUTRITION_PHOTO,
    ROUTE_NUTRITION_REVIEW, ROUTE_NUTRITION_ADD, ROUTE_NUTRITION_VOICE,
    ROUTE_NUTRITION_ANALYZING, ROUTE_NUTRITION_MANUAL, ROUTE_PROGRESS_TODAY,
    ROUTE_PROGRESS_WORKOUT, ROUTE_PROGRESS_NUTRITION, ROUTE_PROGRESS_HEALTH, ROUTE_PROGRESS_WEEKLY
)

@Composable
fun VoraNavHost(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar

    val window = LocalActivity.current?.window
    val view = LocalView.current
    val darkStatusBar = currentRoute in darkRoutes
    SideEffect { window?.let { WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkStatusBar } }

    val selectedTab = if (currentRoute == Routes.NOTIFICATIONS) 0 else bottomTabs.indexOfFirst { currentRoute?.startsWith(it) == true }

    val goTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VoraBottomBar(
                    selectedIndex = selectedTab.coerceAtLeast(0),
                    onSelect = { goTab(bottomTabs[it]) }
                )
            }
        },
        contentWindowInsets = if (currentRoute in edgeToEdgeRoutes) WindowInsets(0) else ScaffoldDefaults.contentWindowInsets,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            composable(Routes.LOGIN) {
                PlaceholderScreen("Iniciar sesión", Routes.HOME to "Entrar", Routes.SIGNUP to "Crear cuenta") { route ->
                    if (route == Routes.HOME) navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                    else navController.navigate(route)
                }
            }
            composable(Routes.SIGNUP) {
                PlaceholderScreen("Crear cuenta", Routes.HOME to "Registrarme", Routes.LOGIN to "Iniciar sesión") { route -> navController.navigate(route) }
            }
            composable(Routes.HOME) {
                PlaceholderScreen("Inicio", Routes.NOTIFICATIONS to "Notificaciones", ROUTE_COACH to "Asistente VORA", Routes.WORKOUT to "Entrenamiento") { route -> navController.navigate(route) }
            }
            composable(Routes.NOTIFICATIONS) {
                PlaceholderScreen("Notificaciones")
            }

            // --- COACH ---
            composable(ROUTE_COACH) { PlaceholderScreen("Asistente VORA") }
            composable(ROUTE_COACH_LISTENING) { PlaceholderScreen("Escuchando") }
            composable(ROUTE_COACH_PROCESSING) { PlaceholderScreen("Procesando") }
            composable(ROUTE_COACH_SPEAKING) { PlaceholderScreen("Hablando") }
            composable(ROUTE_COACH_CHAT) { PlaceholderScreen("Conversación") }
            composable(ROUTE_COACH_ERROR) { PlaceholderScreen("Error") }

            // --- ENTRENAMIENTO (Tus pantallas reales) ---
            composable(Routes.WORKOUT) {
                WorkoutCatalogScreen(
                    onWorkoutClick = { workoutId -> navController.navigate(Routes.workoutDetail(workoutId)) },
                    onPlanClick = { navController.navigate(Routes.WORKOUT_PLAN) }
                )
            }
            composable(Routes.WORKOUT_PLAN) {
                val workoutEntry = rememberWorkoutEntry(navController)
                val viewModel = hiltViewModel<WorkoutViewModel>(workoutEntry)
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                WorkoutPlanScreen(
                    workouts = state.workouts,
                    onWorkoutClick = { workoutId -> navController.navigate(Routes.workoutDetail(workoutId)) },
                    onStartWorkout = { workoutId -> navController.navigate(Routes.workoutPrep(workoutId)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Routes.WORKOUT_DETAIL, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                WorkoutDetailScreen(
                    workoutId = workoutId,
                    onBack = { navController.popBackStack() },
                    onStartWorkout = { navController.navigate(Routes.workoutPrep(workoutId)) },
                    onExerciseClick = { exerciseId -> navController.navigate(Routes.workoutGuide(workoutId, exerciseId)) },
                    viewModel = viewModel
                )
            }
            composable(route = Routes.WORKOUT_PREP, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutPrepScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onStartWorkout = { navController.navigate(Routes.workoutActive(workoutId)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Routes.WORKOUT_ACTIVE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutActiveScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onGuideClick = { exerciseId -> navController.navigate(Routes.workoutGuide(workoutId, exerciseId)) },
                    onFinish = { navController.navigate(Routes.workoutDone(workoutId)) }
                )
            }
            composable(route = Routes.WORKOUT_GUIDE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType }, navArgument("exerciseId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val exerciseId = entry.arguments?.getString("exerciseId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                ExerciseGuideScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    exerciseId = exerciseId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Routes.WORKOUT_DONE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutCompletedScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onHome = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true }; launchSingleTop = true } },
                    onBackToWorkout = { navController.navigate(Routes.workoutDetail(workoutId)) { popUpTo(Routes.WORKOUT) { inclusive = false }; launchSingleTop = true } }
                )
            }

            // --- NUTRICIÓN ---
            composable(ROUTE_NUTRITION) { PlaceholderScreen("Nutrición") }
            composable(ROUTE_NUTRITION_MEALS) { PlaceholderScreen("Comidas") }
            composable(ROUTE_NUTRITION_ADD) { PlaceholderScreen("Agregar") }
            composable(ROUTE_NUTRITION_VOICE) { PlaceholderScreen("Voz") }
            composable(ROUTE_NUTRITION_ANALYZING) { PlaceholderScreen("Analizando") }
            composable(ROUTE_NUTRITION_PHOTO) { PlaceholderScreen("Foto") }
            composable(ROUTE_NUTRITION_REVIEW) { PlaceholderScreen("Review") }
            composable(ROUTE_NUTRITION_MANUAL) { PlaceholderScreen("Manual") }

            // --- PROGRESO ---
            composable(ROUTE_PROGRESS) { PlaceholderScreen("Progreso") }
            composable(ROUTE_PROGRESS_TODAY) { PlaceholderScreen("Hoy") }
            composable(ROUTE_PROGRESS_WORKOUT) { PlaceholderScreen("Entrenamiento") }
            composable(ROUTE_PROGRESS_NUTRITION) { PlaceholderScreen("Nutrición") }
            composable(ROUTE_PROGRESS_HEALTH) { PlaceholderScreen("Salud") }
            composable(ROUTE_PROGRESS_WEEKLY) { PlaceholderScreen("Semanal") }

            // --- PERFIL ---
            composable(ROUTE_PROFILE) { PlaceholderScreen("Perfil") }
            composable(ROUTE_PROFILE_EDIT) { PlaceholderScreen("Editar perfil") }
            composable(ROUTE_PROFILE_GOALS) { PlaceholderScreen("Objetivos") }
            composable(ROUTE_PROFILE_NUTRITION) { PlaceholderScreen("Preferencias nutrición") }
            composable(ROUTE_PROFILE_VOICE) { PlaceholderScreen("Voz") }
            composable(ROUTE_PROFILE_SETTINGS) { PlaceholderScreen("Ajustes") }
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
    onNavigate: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = title)
        links.forEach { (route, label) ->
            Button(onClick = { onNavigate(route) }) {
                Text(text = label)
            }
        }
    }
}
