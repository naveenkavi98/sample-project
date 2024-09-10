package com.example.sample.Utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaCodec
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.sample.BuildConfig
import com.example.sample.R
import java.io.File

class DownloadController(private val context: Context, private val fileURL: String?, private val fileName: String, private val type : String) {

    fun download() {
        var destination = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        ).toString() + "/"
        destination += fileName
        Log.e( "download: ", fileURL.toString())
        val uri = Uri.parse("file://$destination")

        val file = File(destination)
        if (file.exists()) file.delete()

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(fileURL)
        val request = DownloadManager.Request(downloadUri)
        request.setMimeType(MediaCodec.MetricsConstants.MIME_TYPE)
        request.setTitle(context.resources.getString(R.string.app_name))
        request.setDescription(fileName)

        // set destination
        request.setDestinationUri(uri)

        showPdf(file)
        // Enqueue a new download and same the referenceId
        downloadManager.enqueue(request)
        Toast.makeText(context, "downloading", Toast.LENGTH_SHORT).show()
    }

    private fun showPdf(file: File) {
        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".provider",file
                    )
                    intent.setDataAndType(contentUri, type)
                }
                else {
                    intent.setDataAndType(Uri.fromFile(file), type)
                }
                try {
                    context.startActivity(intent)
                    context.unregisterReceiver(this)
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(onComplete, intentFilter, RECEIVER_EXPORTED)
        }
        else {
            context.registerReceiver(onComplete, intentFilter)
        }
    }
}