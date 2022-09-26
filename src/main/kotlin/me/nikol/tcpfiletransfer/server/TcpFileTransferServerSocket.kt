package me.nikol.tcpfiletransfer.server

import me.nikol.tcpfiletransfer.readLine
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

class TcpFileTransferServerSocket(port: Int) : AutoCloseable {
    private val serverSocket: ServerSocket

    init {
        serverSocket = ServerSocket(port)
    }

    fun accept(): Socket {
        return serverSocket.accept()
    }

    fun receiveFileInfo(clientSocket: Socket): FileInfo {
        val clientInputStream = clientSocket.getInputStream()

        return FileInfo(
            clientInputStream.readLine(),
            clientInputStream.readLine().toLong()
        )
    }

    fun receiveFile(clientSocket: Socket, outputStream: OutputStream) {
        clientSocket.getInputStream().transferTo(outputStream)
    }

    fun responseAboutFileReceive(clientSocket: Socket, wasSendingSuccess: Boolean) {
        val response = if (wasSendingSuccess) "transfer completed\n" else "transfer failed\n"
        clientSocket.getOutputStream().write(response.toByteArray())
    }

    fun disconnect(clientSocket: Socket) {
        clientSocket.close()
    }

    override fun close() {
        serverSocket.close()
    }
}

