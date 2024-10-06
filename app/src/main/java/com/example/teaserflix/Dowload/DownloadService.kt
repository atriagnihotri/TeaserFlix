package com.example.teaserflix.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.teaserflix.Dowload.Downloader

class DownloadService(private val context: Context) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadfile(url: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setMimeType("video/mp4")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("video.mp4")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.mp4")

        return downloadManager.enqueue(request)
    }

}
