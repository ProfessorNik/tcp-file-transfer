package me.nikol.tcpfiletransfer.server

import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileOutputStream
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ClientHandler(private val server: TcpFileTransferServerSocket, private val client: Socket) {
    private val logger = LogManager.getLogger(ClientHandler::class.qualifiedName)
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private lateinit var fileInfo: FileInfo
    private lateinit var downloadSpeedCounter: DownloadSpeedCounter
    private val downloadSpeedLog = Runnable {
        logger.info("$fileInfo download speed: ${downloadSpeedCounter.downloadSpeedInKbPerSec()} kb/sec")
    }


    fun handle(){
        fileInfo = server.receiveFileInfo(client)
        transferStartLog()
        downloadSpeedCounter = DownloadSpeedCounter(fileInfo)

        receiveFile()
        downloadSpeedLog.run()
        downloadSpeedPerSessionLog()

        server.responseAboutFileReceive(client, wasFileReceivedSuccessfully(fileInfo))
        resultLog()
    }

    private fun receiveFile() {
        try {
            executor.scheduleWithFixedDelay(downloadSpeedLog, 3000, 3000, TimeUnit.MILLISECONDS)

            File(fileInfo.fullPath).mkdirs()
            FileOutputStream(fileInfo.fileNameWithFullPath).use {
                server.receiveFile(client, it)
            }
        } finally {
            executor.shutdown()
        }
    }

    private fun transferStartLog(){
        logger.info("$fileInfo transfer start")
    }

    private fun downloadSpeedPerSessionLog(){
        logger.info("$fileInfo download speed per session: ${downloadSpeedCounter.downloadSpeedPerSessionInKbPerSec()} kb/sec")
    }

    private fun resultLog(){
        if (wasFileReceivedSuccessfully(fileInfo))
            logger.info("$fileInfo transfer completed")
        else
            logger.error("$fileInfo transfer failed")
    }

    private fun wasFileReceivedSuccessfully(fileInfo: FileInfo) : Boolean =
        File(fileInfo.fileName).length() == fileInfo.size
}