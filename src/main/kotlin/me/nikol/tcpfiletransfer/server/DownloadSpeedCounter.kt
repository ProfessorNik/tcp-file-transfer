package me.nikol.tcpfiletransfer.server

import java.lang.System.currentTimeMillis
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class DownloadSpeedCounter(private val fileInfo: FileInfo) {
    private val startCheckTimePerMillis = currentTimeMillis()
    private val startCheckSizePerBytes = 0L
    private var lastCheckTimePerMillis = currentTimeMillis()
    private var lastCheckSizePerBytes = 0L

    fun downloadSpeedInKbPerSec(): Long {
        val fileSizePerBytes = Files.size(Path.of(fileInfo.fileNameWithFullPath))
        val curTimePerMillis = currentTimeMillis()
        val speedInKbPerSec = countDownloadSpeedInKbPerSec(
            curTimePerMillis - lastCheckTimePerMillis,
            fileSizePerBytes - lastCheckSizePerBytes
        )
        lastCheckSizePerBytes = fileSizePerBytes
        lastCheckTimePerMillis = curTimePerMillis
        return speedInKbPerSec
    }

    fun downloadSpeedPerSessionInKbPerSec(): Long {
        val fileSizePerBytes = Files.size(Path.of(fileInfo.fileNameWithFullPath))
        return countDownloadSpeedInKbPerSec(
            currentTimeMillis() - startCheckTimePerMillis,
            fileSizePerBytes - startCheckSizePerBytes
        )
    }

    private fun countDownloadSpeedInKbPerSec(downloadTimePerMillis: Long, downloadSizePerBytes: Long): Long {
        val downloadTimePerSeconds = TimeUnit.MILLISECONDS.toSeconds(downloadTimePerMillis)
        val downloadSizePerKb = downloadSizePerBytes / 1024
        return downloadSizePerKb /
                if (downloadTimePerSeconds == 0L)
                    1L
                else downloadTimePerSeconds
    }
}