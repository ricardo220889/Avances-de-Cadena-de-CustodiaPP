package com.example.cadenacustodiapp.pdf

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfStorageManager {

    fun createPdfUri(context: Context, baseName: String): Uri? {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "${baseName}_$timeStamp.pdf"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            createMediaStoreUri(context, fileName)
        } else {
            createLegacyUri(context, fileName)
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createMediaStoreUri(context: Context, fileName: String): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/CadenaCustodia")
        }

        return context.contentResolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            values
        )
    }


    private fun createLegacyUri(context: Context, fileName: String): Uri? {
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val subDir = java.io.File(downloadsDir, "CadenaCustodia")
        if (!subDir.exists()) subDir.mkdirs()

        val file = java.io.File(subDir, fileName)
        return Uri.fromFile(file)
    }
}