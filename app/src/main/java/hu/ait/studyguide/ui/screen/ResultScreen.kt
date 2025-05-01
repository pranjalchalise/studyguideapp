package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(text: String, onBack: () -> Unit, padding: PaddingValues) =
    CenteredColumn(padding) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Choose again") }
    }
