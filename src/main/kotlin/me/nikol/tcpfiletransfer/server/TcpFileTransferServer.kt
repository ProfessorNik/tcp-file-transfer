package me.nikol.tcpfiletransfer.server

import kotlin.concurrent.thread

class TcpFileTransferServer(private val port: Int){
    fun start() {
        TcpFileTransferServerSocket(port).use {
            thread {
                try {
                    connectClientsToReceiveFiles(it)
                } catch (e: Exception) {
                    println(e.message)
                }
            }.join()
        }
    }

    private fun connectClientsToReceiveFiles(server: TcpFileTransferServerSocket){
        while (true) {
            val client = server.accept()
            thread {
                try {
                    ClientHandler(server, client).handle()
                } catch (e: Exception) {
                    println(e.message)
                    println("transfer failed")
                } finally {
                    server.disconnect(client)
                }
            }
        }
    }

}
