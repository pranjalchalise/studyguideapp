package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CenteredColumn(
    outerPadding: PaddingValues,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(outerPadding)
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = content
)
