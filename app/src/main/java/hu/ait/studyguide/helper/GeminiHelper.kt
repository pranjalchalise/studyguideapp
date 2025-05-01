package hu.ait.studyguide.helper

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object GeminiHelper {

    private const val GEMINI_API_KEY = "AIzaSyDm5v_PjFplxv9TY_3xQ_rr0ZLVe4ugROc"

    private val model by lazy {
        GenerativeModel(
            modelName = "models/gemini-1.5-flash",
            apiKey = GEMINI_API_KEY
        )
    }


    suspend fun askGemini(prompt: String): String = withContext(Dispatchers.IO) {
        runCatching { model.generateContent(prompt).text ?: "" }.getOrDefault("")
    }
}
