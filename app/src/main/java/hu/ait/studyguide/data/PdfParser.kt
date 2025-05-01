package hu.ait.studyguide.data

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader

object PdfParser {

    fun extractText(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Unable to open PDF")

        // 📝 NOTE: This reads the raw bytes as plain text, which only works if PDF is text-based.
        // For proper parsing, you’d use PdfRenderer (API 21+) or do server-side parsing.

        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.readText()
    }
}
