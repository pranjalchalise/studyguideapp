package hu.ait.studyguide.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hu.ait.studyguide.data.pdf.PdfUtils
import kotlinx.coroutines.launch

@Composable
fun PickPdfScreen(onPdfPicked: (String) -> Unit) {
    val ctx   = LocalContext.current
    val scope = rememberCoroutineScope()
    var status by remember { mutableStateOf("Pick a PDF to start") }

    /* ── system file picker ──────────────────────────────────────── */
    val picker = rememberLauncherForActivityResult(OpenDocument()) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            status = "Reading PDF…"
            val text = PdfUtils.extractTextFromPdf(ctx, uri)
            status = "PDF loaded ✔️"
            onPdfPicked(text)
        }
    }

    CenteredColumn {
        ElevatedButton(
            onClick  = { picker.launch(arrayOf("application/pdf")) },
            modifier = Modifier.padding(bottom = 24.dp)
        ) { Text("Choose PDF") }

        Text(status)
    }
}

@Composable
fun CenteredColumn(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier            = Modifier
        .fillMaxSize()
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content             = content
)
