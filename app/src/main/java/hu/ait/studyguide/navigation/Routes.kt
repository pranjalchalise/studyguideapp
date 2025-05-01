package hu.ait.studyguide.navigation

sealed class Routes(val route: String) {
    object PickPdf      : Routes("pick")
    object ModeSelect   : Routes("modeSelect")         // expects pdfText in nav arg
    object Result       : Routes("result")             // expects result text in nav arg
}
