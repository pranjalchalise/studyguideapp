package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun PickPdfScreen(onPick: () -> Unit, padding: PaddingValues) =
    CenteredColumn(padding) {
        Icon(
            Icons.Rounded.PictureAsPdf,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(96.dp)
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onPick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.width(180.dp)
        ) { Text("Choose PDF") }
    }
