package com.example.cadenacustodiapp.pdf

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class PdfViewer {

    fun openPdf(context: Context, uri: Uri) {
        try {

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_ACTIVITY_NO_HISTORY
            }

            context.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "No tienes una app para abrir PDFs",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}