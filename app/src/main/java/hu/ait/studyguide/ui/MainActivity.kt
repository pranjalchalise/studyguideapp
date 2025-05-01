package hu.ait.studyguide.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import hu.ait.studyguide.data.pdf.PdfUtils
import hu.ait.studyguide.helper.GeminiHelper
import hu.ait.studyguide.navigation.Routes
import hu.ait.studyguide.navigation.StudyNavGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val GEMINI_KEY = "AIzaSyDm5v_PjFplxv9TY_3xQ_rr0ZLVe4ugROc"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val ctx          = LocalContext.current
                val scope        = rememberCoroutineScope()
                val nav          = rememberNavController()
                val snackbarHost = remember { SnackbarHostState() }

                var pdfText  by remember { mutableStateOf("") }
                var result   by remember { mutableStateOf("") }
                var mode     by remember { mutableStateOf("NOTES") }
                var loading  by remember { mutableStateOf(false) }

                /* PDF picker launcher */
                val pickPdf = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    uri ?: return@rememberLauncherForActivityResult
                    pdfText = PdfUtils.extractText(ctx, uri) ?: ""
                    nav.navigate(Routes.Mode.route)
                }

                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Study Guide") })
                    },
                    snackbarHost = { SnackbarHost(snackbarHost) }
                ) { padding ->

                    StudyNavGraph(
                        navController = nav,
                        pdfText       = pdfText,
                        resultText    = result,
                        setMode       = { mode = it },
                        onPickPdf     = { pickPdf.launch("application/pdf") },
                        onAskGemini   = {
                            scope.launch(Dispatchers.IO) {
                                loading = true
                                try {
                                    result = GeminiHelper.ask(GEMINI_KEY, mode, pdfText)
                                } catch (e: Exception) {
                                    snackbarHost.showSnackbar("Gemini error: ${e.message}")
                                } finally {
                                    loading = false
                                }
                            }
                        },
                        padding = padding
                    )

                    if (loading) FullScreenLoader()
                }
            }
        }
    }
}

@Composable
fun FullScreenLoader() = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = .35f)),
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}
