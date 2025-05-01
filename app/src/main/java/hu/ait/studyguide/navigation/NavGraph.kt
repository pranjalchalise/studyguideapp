package hu.ait.studyguide.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.studyguide.ui.screen.ModeSelectScreen
import hu.ait.studyguide.ui.screen.PickPdfScreen
import hu.ait.studyguide.ui.screen.ResultScreen

@Composable
fun StudyGuideNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.PickPdf.route) {

        composable(Routes.PickPdf.route) {
            PickPdfScreen { pdfText ->
                nav.navigate("${Routes.ModeSelect.route}/${pdfText.encode()}")
            }
        }

        composable(
            route = "${Routes.ModeSelect.route}/{pdf}",
            arguments = listOf(navArgument("pdf") { type = NavType.StringType })
        ) { back ->
            val pdfText = back.arguments!!.getString("pdf")!!.decode()
            ModeSelectScreen(
                pdfText = pdfText,
                onModeChosen = { mode ->
                    nav.navigate("${Routes.Result.route}/${mode}/${pdfText.encode()}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${Routes.Result.route}/{mode}/{pdf}",
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("pdf")  { type = NavType.StringType }
            )
        ) { back ->
            ResultScreen(
                mode    = back.arguments!!.getString("mode")!!,
                pdfText = back.arguments!!.getString("pdf")!!.decode(),
                onBack  = { nav.popBackStack() }
            )
        }
    }
}

private fun String.encode() = java.net.URLEncoder.encode(this, "UTF-8")
private fun String.decode() = java.net.URLDecoder.decode(this, "UTF-8")
