package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModeSelectScreen(onModeChosen: (String) -> Unit, onBack: () -> Unit) =
    CenteredColumn {
        Text("Choose output type")
        Spacer(Modifier.height(24.dp))

        listOf("NOTES", "QUESTIONS", "SUMMARY").forEach { label ->
            Button(
                onClick = { onModeChosen(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) { Text(label) }
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onBack) { Text("Back") }
    }
