package hu.ait.studyguide.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.ait.studyguide.ui.screen.*

@Composable
fun StudyNavGraph(
    navController: NavHostController,
    pdfText: String,
    resultText: String,
    setMode: (String) -> Unit,
    onPickPdf: () -> Unit,
    onAskGemini: () -> Unit,
    padding: PaddingValues
) {
    NavHost(navController, Routes.Pick.route) {

        composable(Routes.Pick.route) {
            PickPdfScreen(onPickPdf, padding)
        }

        composable(Routes.Mode.route) {
            ModeSelectScreen(
                onModeChosen = { m ->
                    setMode(m)
                    navController.navigate(Routes.Result.route)
                    onAskGemini()
                },
                onBack = { navController.popBackStack() },
                padding = padding
            )
        }

        composable(Routes.Result.route) {
            ResultScreen(resultText, onBack = { navController.popBackStack() }, padding)
        }
    }
}
