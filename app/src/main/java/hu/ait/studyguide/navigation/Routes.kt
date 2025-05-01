package hu.ait.studyguide.navigation

sealed class Routes(val route: String) {
    data object Pick   : Routes("pick")
    data object Mode   : Routes("mode")
    data object Result : Routes("result")
}
