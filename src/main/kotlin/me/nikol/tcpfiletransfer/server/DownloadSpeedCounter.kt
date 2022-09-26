package me.nikol.tcpfiletransfer.server

import java.io.File
import java.lang.System.currentTimeMillis

class DownloadSpeedCounter(private val fileInfo: FileInfo) {
    private val startCheckTime = currentTimeMillis()
    private val startCheckSize = 0L
    private var lastCheckTime = currentTimeMillis()
    private var lastCheckSize = 0L

    fun downloadSpeed(): Long {
        val fileSize = File(fileInfo.fileName).length()
        val curTime = currentTimeMillis()
        val downloadTime = curTime - lastCheckTime
        val downloadSize = fileSize - lastCheckSize
        lastCheckSize = fileSize
        lastCheckTime = curTime
        return downloadSize / downloadTime
    }

    fun downloadSpeedPerSession(): Long {
        val fileSize = File(fileInfo.fileName).length()
        val curTime = currentTimeMillis()
        val downloadTime = curTime - startCheckTime
        val downloadSize = fileSize - startCheckSize
        return downloadSize / downloadTime
    }
}