package hu.ait.studyguide.utils

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper

object PdfUtils {
    /**
     * Reads every page and returns one big String (or null on failure).
     */
    fun extractText(context: Context, uri: Uri): String? = try {
        // pdfbox-android needs this one-time init (safe to call repeatedly)
        PDFBoxResourceLoader.init(context)

        context.contentResolver.openInputStream(uri)?.use { input ->
            PDDocument.load(input).use { doc ->
                PDFTextStripper().getText(doc)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
