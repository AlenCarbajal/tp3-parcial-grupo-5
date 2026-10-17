package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun VoraNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            PlaceholderScreen("Iniciar sesión", Routes.HOME to "Entrar") { navController.navigate(it) }
        }
        composable(Routes.SIGNUP) { PlaceholderScreen("Crear cuenta") {} }
        composable(Routes.HOME) {
            PlaceholderScreen(
                "Inicio",
                Routes.WORKOUT to "Entrenamiento",
                Routes.NUTRITION to "Nutrición",
                Routes.PROGRESS to "Progreso",
                Routes.PROFILE to "Perfil",
                Routes.COACH to "Asistente VORA",
                Routes.NOTIFICATIONS to "Notificaciones",
            ) { navController.navigate(it) }
        }
        composable(Routes.NOTIFICATIONS) { PlaceholderScreen("Notificaciones") {} }
        composable(Routes.COACH) { PlaceholderScreen("Asistente VORA") {} }
        composable(Routes.WORKOUT) { PlaceholderScreen("Entrenamiento") {} }
        composable(Routes.NUTRITION) { PlaceholderScreen("Nutrición") {} }
        composable(Routes.PROGRESS) { PlaceholderScreen("Progreso") {} }
        composable(Routes.PROFILE) { PlaceholderScreen("Perfil") {} }
    }
}

// Pantalla temporal: se reemplaza por la real de cada área.
@Composable
private fun PlaceholderScreen(
    title: String,
    vararg links: Pair<String, String>,
    onNavigate: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Text(title)
        links.forEach { (route, label) ->
            Button(onClick = { onNavigate(route) }) { Text(label) }
        }
    }
}

