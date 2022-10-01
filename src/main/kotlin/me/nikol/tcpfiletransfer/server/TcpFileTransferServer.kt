package me.nikol.tcpfiletransfer.server

import org.apache.logging.log4j.LogManager
import java.util.concurrent.Executors

class TcpFileTransferServer(private val port: Int){
    private val logger = LogManager.getLogger(TcpFileTransferServer::class.qualifiedName)
    private val threadPool = Executors.newCachedThreadPool()

    fun start() {
        TcpFileTransferServerSocket(port).use {
            try {
                connectClientsToReceiveFiles(it)
            } catch (e: Exception) {
                logger.atError().withThrowable(e).log("Connect client error: ")
            } finally {
                threadPool.shutdown()
            }
        }
    }

    private fun connectClientsToReceiveFiles(server: TcpFileTransferServerSocket){
        while (true) {
            val client = server.accept()
            threadPool.execute {
                try {
                    ClientHandler(server, client).handle()
                } catch (e: Exception) {
                    logger.atError().withThrowable(e).log("Transfer failed: ")
                } finally {
                    server.disconnect(client)
                }
            }
        }
    }

}
