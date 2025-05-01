package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PickPdfScreen(onPick: () -> Unit) = CenteredColumn {
    Text("Select a PDF to study")
    Spacer(Modifier.height(16.dp))
    Button(onClick = onPick) { Text("Pick PDF") }
}
