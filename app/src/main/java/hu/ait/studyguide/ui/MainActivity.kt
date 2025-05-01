package hu.ait.studyguide.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import hu.ait.studyguide.data.pdf.PdfUtils
import hu.ait.studyguide.helper.GeminiHelper
import hu.ait.studyguide.navigation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val GEMINI_KEY = "AIzaSyDm5v_PjFplxv9TY_3xQ_rr0ZLVe4ugROc"   // ← your key here

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState).also {
        setContent {
            val ctx   = LocalContext.current
            val scope = rememberCoroutineScope()
            val nav   = rememberNavController()

            var pdfText by remember { mutableStateOf("") }
            var result  by remember { mutableStateOf("") }
            var mode    by remember { mutableStateOf("NOTES") }

            /* PDF picker launcher */
            val pickPdf = rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
                uri ?: return@rememberLauncherForActivityResult
                pdfText = PdfUtils.extractText(ctx, uri) ?: ""
                nav.navigate(Routes.Mode.route)
            }

            StudyNavGraph(
                navController = nav,
                pdfText = pdfText,
                resultText = result,
                setMode = { mode = it },
                onPickPdf = { pickPdf.launch("application/pdf") },
                onAskGemini = {
                    scope.launch(Dispatchers.IO) {
                        result = GeminiHelper.ask(GEMINI_KEY, mode, pdfText)
                    }
                }
            )
        }
    }
}
