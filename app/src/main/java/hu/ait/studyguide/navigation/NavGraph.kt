package hu.ait.studyguide.navigation

import androidx.compose.runtime.Composable
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
    onAskGemini: () -> Unit
) {
    NavHost(navController, Routes.Pick.route) {

        composable(Routes.Pick.route) {
            PickPdfScreen(onPick = onPickPdf)
        }

        composable(Routes.Mode.route) {
            ModeSelectScreen(
                onModeChosen = { label ->
                    setMode(label)
                    navController.navigate(Routes.Result.route)
                    onAskGemini()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Result.route) {
            ResultScreen(text = resultText) { navController.popBackStack() }
        }
    }
}
