package hu.ait.studyguide.data.pdf

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper

object PdfUtils {
    /** Reads every page of the PDF and returns one big String. */
    fun extractText(ctx: Context, uri: Uri): String? = try {
        PDFBoxResourceLoader.init(ctx)          // safe to call repeatedly
        ctx.contentResolver.openInputStream(uri)?.use { input ->
            PDDocument.load(input).use { doc -> PDFTextStripper().getText(doc) }
        }
    } catch (e: Exception) {
        e.printStackTrace(); null
    }
}
