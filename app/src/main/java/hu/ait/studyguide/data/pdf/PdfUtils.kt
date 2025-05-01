package hu.ait.studyguide.data.pdf

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

object PdfUtils {

    private val inited = AtomicBoolean(false)

    private fun ensureInit(ctx: Context) {
        if (inited.compareAndSet(false, true)) {
            PDFBoxResourceLoader.init(ctx.applicationContext)
        }
    }

    suspend fun extractTextFromPdf(ctx: Context, uri: Uri): String =
        withContext(Dispatchers.IO) {
            ensureInit(ctx)
            ctx.contentResolver.openInputStream(uri)?.use { stream ->
                PDDocument.load(stream).use { doc ->
                    PDFTextStripper().getText(doc)
                }
            } ?: ""
        }
}
