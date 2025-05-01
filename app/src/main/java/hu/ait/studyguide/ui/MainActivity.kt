package hu.ait.studyguide.ui          // ← must match AndroidManifest

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.GenerativeModel
import hu.ait.studyguide.utils.PdfUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val GEMINI_API_KEY = "AIzaSyDm5v_PjFplxv9TY_3xQ_rr0ZLVe4ugROc"   // 🔑

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PdfToGeminiScreen()
            }
        }
    }
}

@Composable
fun PdfToGeminiScreen() {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Tap the button and choose a PDF") }

    val pickPdf = rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
        uri ?: return@rememberLauncherForActivityResult
        status = "⏳ Reading PDF…"
        scope.launch(Dispatchers.IO) {
            val text = PdfUtils.extractText(ctx, uri)
            if (text.isNullOrBlank()) {
                status = "❌ Couldn't read that PDF"
                return@launch
            }

            status = "🤖 Talking to Gemini…"
            val model = GenerativeModel(
                modelName = "gemini-1.5-flash",      // works today
                apiKey = GEMINI_API_KEY
            )

            try {
                val reply = model.generateContent(text)
                status = reply.text ?: "🤔 No text in response"
            } catch (e: Exception) {
                status = "❌ Gemini error: ${e.message}"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { pickPdf.launch("application/pdf") }) {
            Text("Select PDF")
        }
        Spacer(Modifier.height(32.dp))
        Text(status)
    }
}
