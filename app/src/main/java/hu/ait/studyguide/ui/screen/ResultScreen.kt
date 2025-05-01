package hu.ait.studyguide.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.ait.studyguide.helper.GeminiHelper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    mode: String,
    pdfText: String,
    onBack: () -> Unit
) {
    var answer by remember { mutableStateOf<String?>(null) }
    var error  by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(mode, pdfText) {
        val prompt = buildPrompt(mode, pdfText)
        runCatching { GeminiHelper.askGemini(prompt) }
            .onSuccess { answer = it }
            .onFailure { error  = it.message }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Guide") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            error != null  -> Centered { Text("⚠️ $error") }
            answer == null -> Centered { CircularProgressIndicator() }
            else           -> ResultContent(
                markdown = answer!!,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


private fun buildPrompt(mode: String, text: String): String {
    val instruction = when (mode) {
        "notes"    -> "Create concise study notes from the following text."
        "quiz"     -> "Generate 5 multiple-choice questions (with answers)."
        "summary"  -> "Write a short, plain-language summary."
        else       -> "Summarise the text."
    }
    return """
        ### Instruction
        $instruction
        
        ### PDF
        $text
    """.trimIndent()
}

private sealed interface Block {
    data class H(val t: String)      : Block
    data class Bullet(val t: String) : Block
    data class P(val t: String)      : Block
}

@Composable
private fun ResultContent(markdown: String, modifier: Modifier = Modifier) {
    val blocks = remember(markdown) { parseMarkdown(markdown) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(blocks) { b ->
            when (b) {
                is Block.H      -> Text(
                    b.t,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                is Block.Bullet -> Row(
                    Modifier.padding(start = 8.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("• ")
                    Text(b.t)
                }
                is Block.P      -> Text(
                    b.t,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

private fun parseMarkdown(src: String): List<Block> = buildList {
    src.lines().forEach { ln ->
        when {
            ln.startsWith("**") && ln.endsWith("**") ->
                add(Block.H(ln.removePrefix("**").removeSuffix("**").trim()))
            ln.startsWith("* ") ->
                add(Block.Bullet(ln.removePrefix("* ").trim()))
            ln.isNotBlank() ->
                add(Block.P(ln.trim()))
        }
    }
}

@Composable
private fun Centered(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content
)
