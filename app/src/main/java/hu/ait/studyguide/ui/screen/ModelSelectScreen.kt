package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.NoteAlt
import androidx.compose.material.icons.rounded.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

private data class ModeCard(val label: String, val icon: ImageVector)

private val MODES = listOf(
    ModeCard("NOTES",     Icons.Rounded.NoteAlt),
    ModeCard("QUESTIONS", Icons.Rounded.Help),
    ModeCard("SUMMARY",   Icons.Rounded.Summarize)
)

@Composable
fun ModeSelectScreen(
    onModeChosen: (String) -> Unit,
    onBack: () -> Unit,
    padding: PaddingValues
) = CenteredColumn(padding) {
    Text("Choose an output type", style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(24.dp))

    MODES.chunked(2).forEach { row ->
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            row.forEach { card ->
                ElevatedCard(
                    onClick = { onModeChosen(card.label) },
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(card.icon, null)
                        Spacer(Modifier.height(8.dp))
                        Text(card.label)
                    }
                }
            }
            if (row.size == 1) Spacer(Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))
    }

    TextButton(onClick = onBack) { Text("Back to file picker") }
}
