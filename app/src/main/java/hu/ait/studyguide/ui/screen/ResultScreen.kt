package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(text: String, onBack: () -> Unit) = CenteredColumn {
    Text("Result")
    Spacer(Modifier.height(12.dp))
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .verticalScroll(rememberScrollState())
    )
    Spacer(Modifier.height(16.dp))
    Button(onClick = onBack) { Text("Back") }
}
