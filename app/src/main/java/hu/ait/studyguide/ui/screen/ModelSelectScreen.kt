package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModeSelectScreen(
    pdfText: String,
    onModeChosen: (String) -> Unit
) {
    CenteredColumn {
        Text("What do you need?", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))

        ModeButton(
            icon = Icons.Default.Description,
            label = "Detailed notes"
        ) { onModeChosen("notes") }

        ModeButton(
            icon = Icons.Default.Quiz,
            label = "Quiz questions"
        ) { onModeChosen("quiz") }

        ModeButton(
            icon = Icons.Default.Lightbulb,
            label = "Short summary"
        ) { onModeChosen("summary") }
    }
}

@Composable
private fun ModeButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) = OutlinedButton(
    onClick = onClick,
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
) {
    Icon(icon, null)
    Spacer(Modifier.width(8.dp))
    Text(label)
}
