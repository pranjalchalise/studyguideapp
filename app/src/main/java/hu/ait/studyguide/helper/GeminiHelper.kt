package hu.ait.studyguide.helper

import com.google.ai.client.generativeai.GenerativeModel

object GeminiHelper {
    private const val MODEL = "gemini-1.5-flash"

    suspend fun ask(apiKey: String, mode: String, text: String): String {
        val prompt = when (mode) {
            "NOTES" -> """
                Produce clear bullet-point study notes with section headings:
                ```$text```
            """.trimIndent()

            "QUESTIONS" -> """
                Generate 10 quiz questions *with answers* that cover key ideas:
                ```$text```
            """.trimIndent()

            else -> /* SUMMARY (fallback) */ """
                Summarize the following text in about 150 words:
                ```$text```
            """.trimIndent()
        }

        val model = GenerativeModel(MODEL, apiKey)
        return model.generateContent(prompt).text ?: "No response"
    }
}
